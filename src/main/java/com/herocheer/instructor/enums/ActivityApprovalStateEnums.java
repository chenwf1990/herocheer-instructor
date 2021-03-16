package com.herocheer.instructor.enums;

/**
 * 活动审核状态
 */
public enum ActivityApprovalStateEnums {
    PENDING(0,"待审核"),
    PASSED(1,"通过"),
    OVERRULE(2,"驳回"),
    WITHDRAW(3,"撤回"),
    DRAFT(4,"草稿");
    ActivityApprovalStateEnums(){

    }

    private int state;
    private String name;

    ActivityApprovalStateEnums(int state, String name) {
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
