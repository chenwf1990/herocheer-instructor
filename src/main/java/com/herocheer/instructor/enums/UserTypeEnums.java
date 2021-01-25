package com.herocheer.instructor.enums;

/**
 * 用户类型
 *
 * @author gaorh
 * @create 2021-01-18
 */
public enum UserTypeEnums {

    weChatUser(1,"公众号用户"),
    instructor(2,"指导员"),
    sysUser(3,"后台用户"),
    sysAdmin(4,"后台管理员");

    private Integer code;
    private String name;

    UserTypeEnums(Integer code, String name) {
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
