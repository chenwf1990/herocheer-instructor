package com.herocheer.instructor.enums;
/**
 * @author linjf
 * @desc 招募类型枚举
 * @date 2021/1/7
 * @company 厦门熙重电子科技有限公司
 */
public enum RecruitTypeEunms {
    STATION_RECRUIT(1,"驿站招募"),
    MATCH_RECRUIT(2,"赛事招募");
    RecruitTypeEunms(){

    }
    private int type;
    private String name;

    RecruitTypeEunms(int type, String name) {
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
