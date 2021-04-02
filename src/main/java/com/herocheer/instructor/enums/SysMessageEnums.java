package com.herocheer.instructor.enums;

/**
 * 消息中心
 *
 * @author gaorh
 * @create 2021-03-29
 */
public enum SysMessageEnums {

    STATION_CHECK("您有一条驿站招募信息待审批，请及时处理！", "驿站招募消息","STATION_CHECK"),
    MATCH_JI0N_CHECK("您有一条赛事活动招募信息待审批，请及时处理！", "赛事活动招募消息","MATCH_JI0N_CHECK"),
    NEWS_JION_CHECK("您有一条新闻活动招募信息待审批，请及时处理！", "新闻活动消息","NEW_JION_CHECK"),
    COURSE_CHECK("您有一条课程信息待审批，请及时处理！", "课程审批消息","COURSE_CHECK"),
    STATION_CARD("您有一条驿站值班补卡待审批，请及时处理！", "驿站值班补卡消息","STATION_CARD"),

    STATION_TIME("您有一条驿站值班时长待审批，请及时处理！", "驿站值班时长消息","STATION_TIME"),
    MATCH_CARD("您有一条赛事活动补卡待审批，请及时处理！", "赛事活动补卡消息","MATCH_CARD"),
    MATCH_TIME("您有一条赛事活动时长待审批，请及时处理！", "赛事活动时长消息","MATCH_TIME"),
    INSTRUCTOR_AUTH("您有一条指导员认证信息待审批，请及时出来！", "指导员认证消息","INSTRUCTOR_AUTH");

    private String text;
    private String type;
    private String code;

    SysMessageEnums(String text, String type,String code) {
        this.text = text;
        this.type = type;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    public String getText() {
        return text;
    }

}
