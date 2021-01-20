package com.herocheer.instructor.enums;

/**
 * @author chenwf
 * @desc
 * @date 2021/1/13
 * @company 厦门熙重电子科技有限公司
 */
public enum ScheduleUserTypeEnums {
    STATION_DUTY(1,"站长"),
    FIXATION_DUTY(2,"固定值班"),
    SUBSCRIBE_DUTY(2,"预约值班");

    private int type;
    private String name;

    ScheduleUserTypeEnums(int type, String name) {
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
