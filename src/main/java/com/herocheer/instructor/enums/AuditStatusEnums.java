package com.herocheer.instructor.enums;

/**
 * @author chenwf
 * @desc 审核状态枚举
 * @date 2021/1/5
 * @company 厦门熙重电子科技有限公司
 */
public enum AuditStatusEnums {
    to_no_audit(0,"不需要审核"),
    to_audit(1,"待审核"),
    to_pass(2,"审核通过");

    private int state;
    private String name;

    AuditStatusEnums(int state, String name) {
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
