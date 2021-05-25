package com.herocheer.instructor.enums;

public enum BorrowStatusEnums {
    to_audit(0,"待审核"),
    to_remand(1,"待签收"),
    to_borrow(2,"待归还"),
    already_borrow(3,"已归还"),
    overrule(4,"驳回"),
    overdue(5,"已过期"),
    cancel(6,"已取消");


    private int status;
    private String name;

    BorrowStatusEnums(int state, String name) {
        this.status = state;
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
