package com.herocheer.instructor.enums;

/**
 * 微信信息常量
 *
 * @author gaorh
 * @create 2021-02-04
 */

public class WechatConst {
    /**
     * session:用户
     */
    public static final String SESSION_USER = "user";

    /**
     * session:验证码
     */
    public static final String SESSION_VERIFICATION_CODE = "verification_code";

    /**
     * token前缀
     */
    public static final String TOKEN_PRE = "token_pre_";

    /**
     * app的验证码过期时间（30分钟）
     */
    public static final long APP_CODE_TIME_OUT = 30 * 60 * 1000;

    /**
     * app的登录超时时间（7天）
     */
    public static final long APP_LOGIN_TIME_OUT = 7 * 24 * 60 * 60 * 1000;


    /**
     * 对称加密规则
     */
    public final static String ASE_ENCODE_RULES = "herocheer";

    /**
     * 用户:admin
     */
    public static final String USER_ADMIN = "admin";


    /**
     * app端传递的token的参
     */
    public static final String TOKEN = "token";

}
