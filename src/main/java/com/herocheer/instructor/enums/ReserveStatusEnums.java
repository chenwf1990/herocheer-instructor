package com.herocheer.instructor.enums;

public enum ReserveStatusEnums {
    ALREADY_RESERVE(0,"已预约"),
    CANCEL_RESERVE(1,"取消预约"),
    EVENT_CANCELED(2,"活动取消"),
    IN_END(3,"已关闭"),
    COURSE_SCHEDULE_CANCELED(6,"课表撤销");
    ReserveStatusEnums(){

    }

    private int state;
    private String name;

    ReserveStatusEnums(int state, String name) {
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
