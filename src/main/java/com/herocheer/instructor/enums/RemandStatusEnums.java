package com.herocheer.instructor.enums;

public enum RemandStatusEnums {
    apply_remand(1,"申请归还"),
    to_confirmed(2,"已归还待确认"),
    already_borrow(3,"已归还");


    private int status;
    private String name;

    RemandStatusEnums(int state, String name) {
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
