package com.herocheer.instructor.service.impl;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.herocheer.cache.bean.RedisClient;
import com.herocheer.common.base.Page.Page;
import com.herocheer.common.exception.CommonException;
import com.herocheer.common.utils.StringUtils;
import com.herocheer.instructor.dao.UserDao;
import com.herocheer.instructor.domain.entity.SysUserRole;
import com.herocheer.instructor.domain.entity.User;
import com.herocheer.instructor.domain.vo.SysUserVO;
import com.herocheer.instructor.enums.UserType;
import com.herocheer.instructor.service.UserService;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        // token:userInfo用户信息
        // TODO tokne-userMenu:menuInfo菜单权限
        // TODO tokne-userArea:areaInfo数据权限 放入redis  可以异步处理
        // 存入redis并设置过期时间为30分钟
        redisClient.set(simpleUUID, JSONObject.toJSONString(user),1800);
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
    public User addUser(SysUserVO sysUserVO) {
        // 判断账号是否存在
        Map<String, Object> objectMap = new HashMap();
        objectMap.put("account", sysUserVO.getAccount());

        if(!ObjectUtils.isEmpty(this.dao.selectSysUserOne(objectMap))){
            throw new CommonException("账号已存在");
        }
        User user = User.builder().userType(UserType.sysUser.getCode()).build();
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
    @Transactional(rollbackFor = Exception.class)
    @Override
    public User modifyUser(SysUserVO sysUserVO) {

        if(sysUserVO.getId() == null || StringUtils.isBlank(sysUserVO.getId().toString())){
            throw new CommonException("编辑ID不能为空");
        }
        // 判断账号是否存在
        /*Map<String, Object> objectMap = new HashMap();
        objectMap.put("account", sysUserVO.getAccount());
        if(!ObjectUtils.isEmpty(this.dao.selectSysUserOne(objectMap))){
            throw new CommonException("账号已存在");
        }*/

        User user = User.builder().userType(UserType.sysUser.getCode()).build();
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
     * 生成令牌
     * JWT生成Token
     * JWT构成: header, payload, signature
     * @param user_id 登录成功后用户user_id, 参数user_id不可传空
     * @return {@link String}
     * @throws Exception 异常
     */
    private static String generateToken(Long user_id) throws Exception {
        Date iatDate = new Date();
        // expire time
        Calendar nowTime = Calendar.getInstance();
        //  token 过期时间: 10天
        nowTime.add(Calendar.DATE, 10);
        Date expiresDate = nowTime.getTime();

        // header Map
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        // build token
        // param backups {iss:Service, aud:APP}
        String token = JWT.create().withHeader(map) // header
                .withClaim("iss", "Service") // payload
                .withClaim("aud", "APP").withClaim("user_id", null == user_id ? null : user_id.toString())
                .withIssuedAt(iatDate) // sign time
                .withExpiresAt(expiresDate) // expire time
                .sign(Algorithm.HMAC256("com.herocheer.instructor")); // signature
        return token;
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
}