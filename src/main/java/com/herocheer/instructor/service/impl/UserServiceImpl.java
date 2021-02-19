package com.herocheer.instructor.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.herocheer.cache.bean.RedisClient;
import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.common.exception.CommonException;
import com.herocheer.common.utils.StringUtils;
import com.herocheer.instructor.aspect.SysLog;
import com.herocheer.instructor.dao.UserDao;
import com.herocheer.instructor.domain.entity.Instructor;
import com.herocheer.instructor.domain.entity.SysUserRole;
import com.herocheer.instructor.domain.entity.User;
import com.herocheer.instructor.domain.vo.AreaPermissionVO;
import com.herocheer.instructor.domain.vo.MemberVO;
import com.herocheer.instructor.domain.vo.SysUserVO;
import com.herocheer.instructor.domain.vo.UserGuideProjectVo;
import com.herocheer.instructor.domain.vo.UserInfoVo;
import com.herocheer.instructor.domain.vo.WeChatUserVO;
import com.herocheer.instructor.enums.CacheKeyConst;
import com.herocheer.instructor.enums.OperationConst;
import com.herocheer.instructor.enums.UserTypeEnums;
import com.herocheer.instructor.service.InstructorService;
import com.herocheer.instructor.service.UserService;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author gaorh
 * @desc 微信用户、后台用户、后台管理员(User)表服务实现类
 * @date 2021-01-18 15:45:22
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Slf4j
public class UserServiceImpl extends BaseServiceImpl<UserDao, User, Long> implements UserService {

    /**
     * 数据加密，在启动类中已经注入进IOC容器中
     */
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private RedisClient redisClient;

    @Resource
    private InstructorService instructorService;

    /**
     * 检查账号
     *
     * @param user 系统用户
     * @param verCode 验证码
     */
    private void checkAcount(User user, String password, String verCode) {
        /*// 判断验证码
        String redisCode = "HEROCHEER-INSTRUCTOR-"+ verCode.trim();
        if (!redisClient.hasKey(redisCode)) {
            throw new CommonException("验证码不正确");
        }*/

        // 检查登入账号是否正确
        if(ObjectUtils.isEmpty(user)){
            throw new CommonException("账号不正确");
        }

        //  检查登入密码是否正确
        if(!encoder.matches(password,user.getPassword())){
            throw new CommonException("密码不正确");
        }
    }

    /**
     * 登录
     *
     * @param account  账号
     * @param password 密码
     * @return {@link String}
     */
    @SysLog(module = "系统管理",bizType = OperationConst.SELECT,bizDesc = "后台用户登入")
    @Override
    public String login(String account, String password){

        Map<String, Object> objectMap = new HashMap();
        objectMap.put("account", account);
        User user = this.dao.selectSysUserOne(objectMap);

        // 登入验证账号（只支持账号）同时登入和密码
        this.checkAcount(user,password,null);

        // 生成的是不带-的字符串，类似于：b17f24ff026d40949c85a24f4f375d42
        String simpleUUID = IdUtil.simpleUUID();

        // 敏感信息不放Redis
        user.setPassword(null);

        //  todo token:userInfo用户信息,存入redis并设置过期时间为30分钟
        redisClient.set(simpleUUID, JSONObject.toJSONString(user), CacheKeyConst.EXPIRETIME);

        // 统一异步处理
        //  当前用户的角色信息放入缓存
        this.findRoleByCurrentUser(user);

        // token:menu-menuInfo 菜单权限  菜单权限树
        // redisClient.set(simpleUUID+":menu",this.findMenuByCurrentUser(user.getId()),1800);
        return simpleUUID;
    }

    /**
     * token:用户相关信息放入redis
     *
     * @param token 令牌
     */
    @Override
    public void hsetUserInfoToRedis(String token) {

    }

    /**
     * 添加用户
     *
     * @param sysUserVO 用户签证官
     * @return {@link User}
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    @SysLog(module = "系统管理",bizType = OperationConst.REGISTER,bizDesc = "用户注册")
    public User addUser(SysUserVO sysUserVO) {
        // 判断账号是否存在
        Map<String, Object> objectMap = new HashMap();
        objectMap.put("account", sysUserVO.getAccount());

        if(!ObjectUtils.isEmpty(this.dao.selectSysUserOne(objectMap))){
            throw new CommonException("账号已存在");
        }
        User user = User.builder().userType(UserTypeEnums.sysUser.getCode()).build();
        BeanCopier.create(sysUserVO.getClass(),user.getClass(),false).copy(sysUserVO,user,null);

        // 用户密码加密
        user.setPassword(encoder.encode(sysUserVO.getPassword()));

        // 插入用户信息
        this.insert(user);

        // 批量插入中间表
        this.batchSysUserRole(sysUserVO, user);

        log.info("用户{}注册成功",user.getUserName());
        return user;
    }

    /**
     * 批处理系统用户角色中间表
     *
     * @param sysUserVO VO
     * @param user      用户
     */
    private void batchSysUserRole(SysUserVO sysUserVO, User user) {
        if(StringUtils.isNotBlank(sysUserVO.getRoleId())){
            // 编辑时删除缓存
            String key = null;
            if(sysUserVO.getId() != null && StringUtils.isNotBlank(sysUserVO.getId().toString())){
                key = StrUtil.format(CacheKeyConst.ROLEID, user.getPhone(), user.getId());
                redisClient.delete(key);
            }

            String[] arr = sysUserVO.getRoleId().split(",");
            List<SysUserRole> list = new ArrayList<>();
            SysUserRole sysUserRole = null;
            for (int i = 0; i < arr.length; i++) {
                sysUserRole = new SysUserRole();
                sysUserRole.setUserId(user.getId());
                sysUserRole.setRoleId(Long.parseLong(arr[i]));
                list.add(sysUserRole);
            }
            this.dao.insertBatchSysUserRole(list);

            // 编辑时重置缓存
            if(sysUserVO.getId() != null && StringUtils.isNotBlank(sysUserVO.getId().toString())){
                redisClient.set(key,sysUserVO.getRoleId(),CacheKeyConst.EXPIRETIME);
            }
        }
    }

    /**
     * 寻找用户信息列表
     *
     * @param sysUserVO 用户签证官
     * @return {@link Page <User>}
     */
    @Override
    public Page<User> findUserByPage(SysUserVO sysUserVO) {
        Page page = Page.startPage(sysUserVO.getPageNo(), sysUserVO.getPageSize());
        List<User> sysUserList = this.dao.selectSysUserByPage(sysUserVO);
        page.setDataList(sysUserList);
        return page;
    }

    /**
     * 根据userId查找用户信息
     *
     * @param id id
     * @return {@link SysUserVO}
     */
    @Override
    public SysUserVO findUserById(Long id) {
        User user = this.get(id);
        if(ObjectUtils.isEmpty(user)){
            throw new CommonException("暂无此个人信息");
        }

        SysUserVO sysUser = SysUserVO.builder().build();
        BeanCopier.create(user.getClass(),sysUser.getClass(),false).copy(user,sysUser,null);

        // 用户角色信息
        String roleId = String.join(",", this.dao.findRoleByUserId(id));
        sysUser.setRoleId(roleId);
        sysUser.setPassword(null);
        return sysUser;
    }

    /**
     * 修改用户
     *
     * @param sysUserVO 用户签证官
     * @return {@link User}
     */
    @SysLog(module = "系统管理",bizType = OperationConst.UPDATE,bizDesc = "修改用户")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public User modifyUser(SysUserVO sysUserVO) {
        if(sysUserVO.getId() == null || StringUtils.isBlank(sysUserVO.getId().toString())){
            throw new CommonException("编辑ID不能为空");
        }

        // 判断用户名是否修改
        if(!sysUserVO.getUserName().equals(this.get(sysUserVO.getId()).getUserName())){
            throw new CommonException("用户名不能修改");
        }

        User user = User.builder().userType(UserTypeEnums.sysUser.getCode()).build();
        BeanCopier.create(sysUserVO.getClass(),user.getClass(),false).copy(sysUserVO,user,null);

        int l = this.dao.update(user);
        log.info("用户{}修改成功",user.getUserName());

        // 删除中间表记录
        if(l > 0){
            this.dao.deleteSysUserRole(sysUserVO.getId());
            this.batchSysUserRole(sysUserVO, user);
        }
        return user;
    }

    /**
     * 修改密码
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    @SysLog(module = "系统管理",bizType = OperationConst.UPDATE,bizDesc = "修改密码")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modifyPassword(Long userId,String oldPassword, String newPassword) {
        // 用户账号
        User user = this.get(userId);
        if(!encoder.matches(oldPassword,user.getPassword())){
            throw new CommonException("原密码输入不正确");
        }

        if(encoder.matches(newPassword,user.getPassword())){
            throw new CommonException("原密码和新密码一样");
        }

        // 修改密码 密码不放缓存
        user.setPassword(encoder.encode(newPassword));
        this.update(user);
    }

    /**
     * 重置密码
     *
     * @param userId 用户id
     */
    @SysLog(module = "系统管理",bizType = OperationConst.UPDATE,bizDesc = "重置密码")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult resetPassword(Long userId) {
        User user = this.get(userId);
        if(!ObjectUtils.isEmpty(user)){
            user.setPassword(encoder.encode("123456"));
            // 重置密码为：123456
            this.update(user);
            return ResponseResult.ok();
        }
        return ResponseResult.fail();
    }

    /**
     * 根据openId获取微信用户信息
     *
     * @param openId id
     * @return {@link User}
     */
    @Override
    public User findUserByOpenId(String openId) {
        Map<String, Object> objectMap = new HashMap();
        objectMap.put("openid", openId);
        return this.dao.selectSysUserOne(objectMap);
    }

    /**
     * 根据电话获取微信用户信息
     *
     * @param phone
     * @return {@link User}
     */
    @Override
    public User findUserByPhone(String phone) {
        Map<String, Object> objectMap = new HashMap();
        objectMap.put("phone", phone);
        return this.dao.selectSysUserOne(objectMap);
    }

    /**
     * 添加微信用户
     *
     * @param weChatUserVO VO
     * @return {@link User}
     */
    @SysLog(module = "系统管理",bizType = OperationConst.INSERT,bizDesc = "添加微信用户")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public User addWeChatUser(WeChatUserVO weChatUserVO) {
        User user = User.builder().userType(UserTypeEnums.weChatUser.getCode()).build();
        BeanCopier.create(weChatUserVO.getClass(),user.getClass(),false).copy(weChatUserVO,user,null);

        // 判断账号是否存在
        Map<String, Object> objectMap = new HashMap();
        objectMap.put("openid", weChatUserVO.getOpenid());
        // 防重
        if(!ObjectUtils.isEmpty(this.dao.selectSysUserOne(objectMap))){
            throw new CommonException("用户已存在");
        }
        this.insert(user);
        return user;
    }

    /**
     * 修改微信用户
     *
     * @param weChatUserVO VO
     * @return {@link User}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public User modifyWeChatUser(WeChatUserVO weChatUserVO) {
        if(weChatUserVO.getId() == null || StringUtils.isBlank(weChatUserVO.getId().toString())){
            throw new CommonException("编辑ID不能为空");
        }

        User user = User.builder().build();
        BeanCopier.create(weChatUserVO.getClass(),user.getClass(),false).copy(weChatUserVO,user,null);

        this.update(user);
        return user;
    }

    /**
     * 查询用户信息
     *
     * @return {@link List<SysUserVO>}
     */
    @Override
    public List<MemberVO> findUser() {
        List<User> userList = this.dao.selectSysUserByPage(new SysUserVO());
        List<MemberVO> memberList = new ArrayList<>();
        MemberVO memberVO = null;
        if(!CollectionUtils.isEmpty(userList)){
            for(User user:userList){
                memberVO = MemberVO.builder().build();
                BeanCopier.create(user.getClass(),memberVO.getClass(),false).copy(user,memberVO,null);
                memberVO.setUserId(user.getId());
                memberList.add(memberVO);
            }
        }
        return memberList;
    }

    /**
     * 根据userType查询用户
     *
     * @param userType 用户类型
     * @return {@link List<MemberVO>}
     */
    @Override
    public List<MemberVO> findUserByUserType(String userType) {
        List<String> stringList = Arrays.asList(userType.split(","));
        return this.dao.findUserByUserType(stringList);
    }

    /**
     * 添加用户信息
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public User addUser(String name, String cardNo, Integer sex, String phone, Integer userType) {
        // 判断身份证是否存在是否存在
        Map<String, Object> params = new HashMap();
        params.put("certificateNo", cardNo);
        List<User> users = this.dao.findByLimit(params);
        User user = new User();
        if(!users.isEmpty()){
            user = users.get(0);
            if(user.getUserType().equals(UserTypeEnums.weChatUser.getCode())){
                user.setUserType(UserTypeEnums.instructor.getCode());
            }
            return user;
        }
        user.setCertificateNo(cardNo);
        user.setUserName(name);
        user.setPhone(phone);
        user.setUserType(userType);
        user.setSex(sex);
        this.dao.insert(user);
        return user;
    }

    /**
     * 根据用户姓名查找用户信息
     *
     * @param userNames
     * @return
     */
    @Override
    public List<User> findUserByUserNames(List<String> userNames) {
        return this.dao.findUserByUserNames(userNames);
    }

    /**
     * 根据用户姓名查找用户信息（指导项目）
     *
     * @param userNames
     * @return
     */
    @Override
    public List<UserGuideProjectVo> findUserProjectByUserNames(List<String> userNames) {
        return this.dao.findUserProjectByUserNames(userNames);
    }

    /**
     * 根据用户姓名查找用户信息（指导项目）
     *
     * @param userIds
     * @return
     */
    @Override
    public List<UserGuideProjectVo> findUserProjectByUserIds(List<Long> userIds) {
        return this.dao.findUserProjectByUserIds(userIds);
    }

    /**
     * 通过当前用户找到区域
     *
     * @param userId 用户id
     * @return {@link AreaPermissionVO}
     */
    @Override
    public AreaPermissionVO findAreaByCurrentUser(Long userId) {
        List<JSONObject> list = this.dao.selectedArea(userId);
        AreaPermissionVO areaPermission = AreaPermissionVO.builder().build();
        if(!CollectionUtils.isEmpty(list)){
            List<String> codeList = list.stream().map(json -> json.getString("areaCode")).collect(Collectors.toList());
            areaPermission.setAreaCode(String.join(",", codeList));
            List<String> strList = list.stream().map(json -> json.getString("areaId")).collect(Collectors.toList());
            areaPermission.setAreaId(String.join(",", strList));
        }
        return areaPermission;
    }

    /**
     * 当前用户的菜单
     *
     * @param id id
     * @return {@link String}
     */
    @Override
    public String findMenuByCurrentUser(Long id) {
        return JSON.toJSONString(this.dao.selectedMenu(id));
    }

    /**
     * 角色权限放进缓存
     *
     * @param user 用户
     */
    @Override
    public void findRoleByCurrentUser(User user) {

        List<String> list = findRoleId(user.getId());
//        List<String> list = this.dao.selectedRole(41L);
        if(CollectionUtils.isEmpty(list)){
            return;
        }
        String idStr = StrUtil.format(CacheKeyConst.ROLEID, user.getPhone(), user.getId());
        redisClient.set(idStr,String.join(",", list),CacheKeyConst.EXPIRETIME);
    }

    /**
     * 找到用户的角色权限
     *
     * @param id id
     * @return {@link List<String>}
     */
    @Override
    public List<String> findRoleId(Long id) {
        return this.dao.selectedRole(id);
    }

    /**
     * 删除系统用户
     *
     * @param id id
     * @return int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeSysUserById(Long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        map.put("id", id);
        return this.dao.delectSysUserById(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(User user) {
        this.dao.update(user);
    }

    /**
     * 查找用户信息
     *
     * @param curUserId
     * @return
     */
    @Override
    public UserInfoVo findUserInfo(Long curUserId) {
        User user = this.dao.get(curUserId);
        UserInfoVo infoVo = new UserInfoVo();
        BeanUtils.copyProperties(user,infoVo);
        //查询是否是指导员
        boolean instructorFlag = true;
        if(!user.getUserType().equals(UserTypeEnums.instructor.getCode())) {
            Instructor instructor = instructorService.findInstructorByUserId(curUserId);
            instructorFlag = instructor == null ? false : true;
        }
        infoVo.setInstructorFlag(instructorFlag);
        return infoVo;
    }
}