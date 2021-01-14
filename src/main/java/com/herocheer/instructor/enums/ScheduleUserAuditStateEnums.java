package com.herocheer.instructor.enums;

/**
 * @author chenwf
 * @desc 值班人员审核状态枚举
 * @date 2021/1/5
 * @company 厦门熙重电子科技有限公司
 */
public enum ScheduleUserAuditStateEnums {
    to_audit(0,"待审核"),
    to_reject(1,"通过"),
    to_pass(2,"驳回");


    private int state;
    private String name;

    ScheduleUserAuditStateEnums(int state, String name) {
        this.state = state;
        this.name = name;
    }


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
