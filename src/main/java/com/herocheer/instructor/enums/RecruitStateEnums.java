package com.herocheer.instructor.enums;

/**
 * @author linjf
 * @desc 招募信息状态枚举
 * @date 2021/1/7
 * @company 厦门熙重电子科技有限公司
 */
public enum RecruitStateEnums {
    TO_RECRUITED(0,"待启动"),
    RECRUITMENT(1,"招募中"),
    END_RECRUITED(2,"招募结束"),
    ACTIVITY_ENDS(3,"已结项"),
    EVENT_CANCELED(4,"活动取消");

    RecruitStateEnums(){

    }

    private int state;
    private String name;

    RecruitStateEnums(int state, String name) {
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
