package com.herocheer.instructor.enums;

/**
 * 缓存KEY常量
 *
 * @author gaorh
 * @create 2021-01-27
 */
public class CacheKeyConst {

    public static final Long EXPIRETIME = 3600L;

    public static final String  AREAID= ":area:id";
    public static final String AREACODE = ":area:code";

    /**
     * role:phone:id
     */
    public static final String ROLEID = "role:{}:{}";

    /**
     * role:phone:code
     */
    public static final String ROLECODE = "role:{}:{}";

    public static final String MENUID = ":menu:id";
    public static final String MENUCODE = ":menu:code";

}
