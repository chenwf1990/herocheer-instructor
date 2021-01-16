package com.herocheer.instructor.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.herocheer.cache.bean.RedisClient;
import com.herocheer.common.base.Page.Page;
import com.herocheer.common.exception.CommonException;
import com.herocheer.instructor.dao.SysUserDao;
import com.herocheer.instructor.domain.entity.SysUser;
import com.herocheer.instructor.domain.vo.SysUserVO;
import com.herocheer.instructor.service.SysUserService;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gaorh
 * @desc 后台用户表（管理员、用户）
 * (SysUser)表服务实现类
 * @date 2021-01-07 17:49:06
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
@Slf4j
public class SysUserServiceImpl extends BaseServiceImpl<SysUserDao, SysUser, Long> implements SysUserService {

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
     * @param sysUser 系统用户
     * @param verCode 验证码
     */
    private void checkAcount(SysUser sysUser,String password,String verCode) {
        // 判断验证码
        String redisCode = "HEROCHEER-INSTRUCTOR-"+ verCode.trim();
        if (!redisClient.hasKey(redisCode)) {
            throw new CommonException("验证码不正确");
        }

        // 检查登入账号是否正确
        if(ObjectUtils.isEmpty(sysUser)){
            throw new CommonException("账号不正确");
        }

        //  检查登入密码是否正确
        if(!encoder.matches(password,sysUser.getPassword())){
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
        SysUser sysUser = this.dao.selectSysUserOne(objectMap);

        // 登入验证账号（只支持账号）同时登入和密码
        this.checkAcount(sysUser,password,verCode);

        // 生成的是不带-的字符串，类似于：b17f24ff026d40949c85a24f4f375d42
        String simpleUUID = IdUtil.simpleUUID();

        // 敏感信息不放Redis
        sysUser.setPassword(null);

        // token:userInfo用户信息
        // TODO tokne-userMenu:menuInfo菜单权限
        // TODO tokne-userArea:areaInfo数据权限 放入redis  可以异步处理
        // 存入redis并设置过期时间为30分钟
        redisClient.set(simpleUUID, JSONObject.toJSONString(sysUser),1800);
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
     * @return {@link SysUser}
     */
    @Override
    public SysUser addUser(SysUserVO sysUserVO) {
        // 判断账号是否存在
        Map<String, Object> objectMap = new HashMap();
        objectMap.put("account", sysUserVO.getAccount());

        if(!ObjectUtils.isEmpty(this.dao.selectSysUserOne(objectMap))){
            throw new CommonException("账号已存在");
        }
        SysUser sysUser = SysUser.builder().build();
        BeanCopier.create(sysUserVO.getClass(),sysUser.getClass(),false).copy(sysUserVO,sysUser,null);

        // 用户密码加密
        sysUser.setPassword(encoder.encode(sysUserVO.getPassword()));

        // 插入用户信息
        this.dao.insert(sysUser);
        log.info("用户{}注册成功",sysUser.getUserName());
        return sysUser;
    }

    /**
     * 寻找用户信息列表
     *
     * @param sysUserVO 用户签证官
     * @return {@link Page <SysUser>}
     */
    @Override
    public Page<SysUser> findUserByPage(SysUserVO sysUserVO) {
        Page page = Page.startPage(sysUserVO.getPageNo(), sysUserVO.getPageSize());
        List<SysUser> sysUserList = this.dao.selectSysUserByPage(sysUserVO);
        page.setDataList(sysUserList);
        return page;
    }

    /**
     * 修改用户
     *
     * @param sysUserVO 用户签证官
     * @return {@link SysUser}
     */
    @Override
    public SysUser modifyUser(SysUserVO sysUserVO) {
        // 判断账号是否存在
        Map<String, Object> objectMap = new HashMap();
        objectMap.put("account", sysUserVO.getAccount());

        if(!ObjectUtils.isEmpty(this.dao.selectSysUserOne(objectMap))){
            throw new CommonException("账号已存在");
        }
        SysUser sysUser = SysUser.builder().build();
        BeanCopier.create(sysUserVO.getClass(),sysUser.getClass(),false).copy(sysUserVO,sysUser,null);

        this.dao.update(sysUser);
        log.info("用户{}修改成功",sysUser.getUserName());
        return sysUser;
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
        SysUser sysUser = this.get(userId);
        if(!encoder.matches(oldPassword,sysUser.getPassword())){
            throw new CommonException("原密码输入不正确");
        }

        if(encoder.matches(newPassword,sysUser.getPassword())){
            throw new CommonException("原密码和新密码一样");
        }

        // 修改密码 密码不放缓存
        sysUser.setPassword(encoder.encode(newPassword));
        this.update(sysUser);
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

    public static void main(String[] args) throws Exception {
        String kk = generateToken(3L);
        System.out.println("JWT---------------------------------------------------");
        System.out.println(kk);
        //生成的是不带-的字符串，类似于：b17f24ff026d40949c85a24f4f375d42
        String simpleUUID = IdUtil.simpleUUID();
        System.out.println("UUID---------------------------------------------------");
        System.out.println(simpleUUID);
        System.out.println(IdUtil.fastUUID());
        System.out.println(IdUtil.fastSimpleUUID());

        //方法2：从Hutool-4.1.14开始提供
        String id2 = IdUtil.objectId();
        System.out.println("OBJ---------------------------------------------------");
        System.out.println(id2);
        //参数1为终端ID
        //参数2为数据中心ID
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        long id = snowflake.nextId();
        System.out.println("Snowflake---------------------------------------------------");
        System.out.println(id);

        // 构建node列表
        List<TreeNode<String>> nodeList = CollUtil.newArrayList();

        Map<String, Object> map = new HashMap();
        map.put("hh", "u------------------------------------------------");
        map.put("test", "k------------------------------------------------");

//        nodeList.add(new TreeNode().setExtra(map));

        nodeList.add(new TreeNode<>("0", "8888888888888888", "超级父类", 5));
        nodeList.add(new TreeNode<>("1", "0", "系统管理", 5).setExtra(map));
        nodeList.add(new TreeNode<>("11", "1", "用户管理", 222222));
        nodeList.add(new TreeNode<>("111", "1", "用户添加", 0));
        nodeList.add(new TreeNode<>("2", "0", "店铺管理", 1));
        nodeList.add(new TreeNode<>("21", "2", "商品管理", 44));
        nodeList.add(new TreeNode<>("221", "2", "商品管理2", 2));

        // 0表示最顶层的id是0
        List<Tree<String>> treeList = TreeUtil.build(nodeList, "8888888888888888");
        System.out.println(JSONObject.toJSONString(treeList));

    }

}