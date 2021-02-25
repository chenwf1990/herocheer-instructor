package com.herocheer.instructor.enums;

/**
 * 用户来源
 *
 * @author gaorh
 * @create 2021-02-08
 */
public enum SourceEnums {
    ixm(1,"i厦门——绑定用户"),
    instructor(2,"后台系统——录入"),
    sysUser(3,"微信公众号——注册");

    private Integer code;
    private String name;

    SourceEnums(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
