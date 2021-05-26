package com.herocheer.instructor.enums;

/**
 * 缓存KEY常量
 *
 * @author gaorh
 * @create 2021-01-27
 */
public class CacheKeyConst {

    public static final Long EXPIRETIME = 1L * 24L * 60L * 60L;

    /**
     * 微信用户基础信息失效时间
     */
    public static final Long OPENID_EXPIRETIME = 30L * 24L * 60L * 60L;


    /**
     * role:phone:id
     */
    public static final String ROLEID = "role:{}:{}";

    /**
     * 访问令牌   token:appid:secret
     */
    public static final String ACCESSTOKEN = "token:{}:{}";
    /**
     * accesstoken 失效时间 (2个小时)
     */
    public static final Long ACCESSTOKEN_EXPIRETIME = 2L * 60L * 60L;

    /**
     * 自动取消预约器材借用的KEY
     */
    public static final String DELAY_AUTO_KEY = "key:equipment:borrow";

    /**
     * 自动取消预约时间 (30分钟)
     */
    public static final Long AUTO_EXPIRETIME =  30L * 60L * 1000;
}
