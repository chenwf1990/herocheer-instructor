package com.herocheer.instructor.enums;

/**
 * 系统标识
 *
 * @author gaorh
 * @create 2021-06-30
 */
public enum  UserMarkEnums {

    INSTRUCTOR(1,"社会体育指导员"),
    FIELD(2,"全民健身设施场地");

    private int code;
    private String name;

    UserMarkEnums(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
