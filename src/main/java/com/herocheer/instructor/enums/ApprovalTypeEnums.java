package com.herocheer.instructor.enums;

/**
 * @program: herocheer-instructor
 * @description:
 * @author: Linjf
 * @create date: 2021-01-19 11:09actual situation
 **/
public enum ApprovalTypeEnums {
    SIGN_TIME(1,"按打卡时间统计"),
    SERVICE_TIME(2,"按服务时间统计"),
    ACTUAL_SITUATION(3,"按实际情况填写");
    ApprovalTypeEnums(){

    }
    private int type;
    private String name;

    ApprovalTypeEnums(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int state) {
        this.type = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
