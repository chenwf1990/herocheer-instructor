package com.herocheer.instructor.enums;

public enum CourseApprovalState {
    PENDING(0,"待审核"),
    PASSED(1,"通过"),
    OVERRULE(2,"驳回"),
    WITHDRAW(3,"撤回");
    CourseApprovalState(){

    }

    private int state;
    private String name;

    CourseApprovalState(int state, String name) {
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
