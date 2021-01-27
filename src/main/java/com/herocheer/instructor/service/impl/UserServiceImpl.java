package com.herocheer.instructor.service.impl;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.herocheer.cache.bean.RedisClient;
import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.ResponseResult;
import com.herocheer.common.exception.CommonException;
import com.herocheer.common.utils.StringUtils;
import com.herocheer.instructor.aspect.SysLog;
import com.herocheer.instructor.dao.UserDao;
import com.herocheer.instructor.domain.entity.SysUserRole;
import com.herocheer.instructor.domain.entity.User;
import com.herocheer.instructor.domain.vo.MemberVO;
import com.herocheer.instructor.domain.vo.SysUserVO;
import com.herocheer.instructor.domain.vo.UserGuideProjectVo;
import com.herocheer.instructor.domain.vo.WeChatUserVO;
import com.herocheer.instructor.enums.CacheKeyConst;
import com.herocheer.instructor.enums.OperationConst;
import com.herocheer.instructor.enums.UserTypeEnums;
import com.herocheer.instructor.service.UserService;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
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

    private static final Long EXPIRETIME = 1800L;
    /**
     * 数据加密，在启动类中已经注入进IOC容器中
     */
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private RedisClient redisClient;

    /**
     * 检查账号
     *
     * @param user 系统用户
     * @param verCode 验证码
     */
    private void checkAcount(User user, String password, String verCode) {
        // 判断验证码
        String redisCode = "HEROCHEER-INSTRUCTOR-"+ verCode.trim();
        if (!redisClient.hasKey(redisCode)) {
            throw new CommonException("验证码不正确");
        }

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
     * @param account 账号
     * @param password 密码
     * @param verCode  版本的代码
     * @return {@link String}
     */
    @SysLog(module = "系统管理",bizType = OperationConst.SELECT,bizDesc = "用户登入")
    @Override
    public String login(String account, String password, String verCode) throws CommonException {

        Map<String, Object> objectMap = new HashMap();
        objectMap.put("account", account);
        User user = this.dao.selectSysUserOne(objectMap);

        // 登入验证账号（只支持账号）同时登入和密码
        this.checkAcount(user,password,verCode);

        // 生成的是不带-的字符串，类似于：b17f24ff026d40949c85a24f4f375d42
        String simpleUUID = IdUtil.simpleUUID();

        // 敏感信息不放Redis
        user.setPassword(null);

        //  todo token:userInfo用户信息,存入redis并设置过期时间为30分钟
        redisClient.set(simpleUUID, JSONObject.toJSONString(user), 1800);
        // 统一异步处理

        //  token:role-roleInfo 角色信息
        redisClient.set(simpleUUID+":role",this.findRoleByCurrentUser(user.getId()),1800);

        // token:menu-menuInfo 菜单权限
        redisClient.set(simpleUUID+":menu",this.findMenuByCurrentUser(user.getId()),1800);

        // token:area-areaInfo 数据权限
        this.findAreaByCurrentUser(simpleUUID,user.getId());
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
     * @param sysUserVO 系统用户签证官
     * @param user      用户
     */
    private void batchSysUserRole(SysUserVO sysUserVO, User user) {
        if(StringUtils.isNotBlank(sysUserVO.getRoleId())){
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
     * @param id id
     * @return {@link User}
     */
    @Override
    public User findUserByOpenId(Long id) {
        Map<String, Object> objectMap = new HashMap();
        objectMap.put("openid", id);
        User user = this.dao.selectSysUserOne(objectMap);
        return user;
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
        User user = this.dao.selectSysUserOne(objectMap);
        return user;
    }

    /**
     * 添加微信用户
     *
     * @param weChatUserVO VO
     * @return {@link User}
     */
    @SysLog(module = "系统管理",bizType = OperationConst.INSERT,bizDesc = "添加微信用户")
    @Override
    public User addWeChatUser(WeChatUserVO weChatUserVO) {
        User user = User.builder().userType(UserTypeEnums.weChatUser.getCode()).build();
        BeanCopier.create(weChatUserVO.getClass(),user.getClass(),false).copy(weChatUserVO,user,null);
        user.setUserName(weChatUserVO.getName());
        user.setPhone(weChatUserVO.getPhoneNo());

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
    public User modifyWeChatUser(WeChatUserVO weChatUserVO) {
        if(weChatUserVO.getId() == null || StringUtils.isBlank(weChatUserVO.getId().toString())){
            throw new CommonException("编辑ID不能为空");
        }

        User user = User.builder().build();
        BeanCopier.create(weChatUserVO.getClass(),user.getClass(),false).copy(weChatUserVO,user,null);
        user.setUserName(weChatUserVO.getName());
        user.setPhone(weChatUserVO.getPhoneNo());

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
     * 添加用户信息
     *
     * @return
     */
    @Override
    public User addUser(String name, String cardNo, Integer sex, String phone, Integer userType) {
        // 判断身份证是否存在是否存在
        Map<String, Object> params = new HashMap();
        params.put("certificateNo", cardNo);
        User user = this.dao.selectSysUserOne(params);
        if(user != null){
            return user;
        }
        user = new User();
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
     * 当前用户的区域
     *
     * @param token  令牌
     * @param userId 用户id
     */
    @Override
    public void findAreaByCurrentUser(String token,Long userId) {
        // 是否存在
        redisClient.delete(token + CacheKeyConst.AREAID);
        redisClient.delete(token + CacheKeyConst.AREACODE);

        List<JSONObject> list = this.dao.selectedArea(userId);

        List<String> codeList = list.stream().map(json -> json.getString("areaCode")).collect(Collectors.toList());
        redisClient.set(token + CacheKeyConst.AREACODE,String.join(",", codeList),EXPIRETIME);

        List<String> strList = list.stream().map(json -> json.getString("areaId")).collect(Collectors.toList());
        redisClient.set(token + CacheKeyConst.AREAID,String.join(",", strList),EXPIRETIME);
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
     * 当前用户的角色
     *
     * @param id id
     * @return {@link String}
     */
    @Override
    public String findRoleByCurrentUser(Long id) {
        List<JSONObject> list = this.dao.selectedRole(id);
        List<String> strList = list.stream().map(JSONObject -> JSONObject.getString("roleId")).collect(Collectors.toList());
        return String.join(",", strList);
    }
}