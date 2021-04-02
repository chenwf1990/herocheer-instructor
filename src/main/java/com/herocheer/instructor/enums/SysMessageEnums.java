package com.herocheer.instructor.enums;

/**
 * 消息中心
 *
 * @author gaorh
 * @create 2021-03-29
 */
public enum SysMessageEnums {
    /**会员卡*/
    station_jion("您有一条驿站招募信息待审批，请及时处理！", "驿站招募消息","stadiumCard_consumptionHistory"),
    match_jion("您有一条赛事活动招募信息待审批，请及时处理！", "赛事活动招募消息","stadiumCard_consumptionHistory");
    /*UAC_USER_RECHARGE("您有一条新闻活动招募信息待审批，请及时处理！", "新闻活动消息","stadiumCard_consumptionHistory"),
    UAC_USER_RECHARGE("您有一条课程信息待审批，请及时处理！", "课程审批消息","stadiumCard_consumptionHistory"),
    UAC_USER_RECHARGE("您有一条驿站值班补卡待审批，请及时处理！", "驿站值班补卡消息","stadiumCard_consumptionHistory"),

    UAC_USER_RECHARGE("您有一条驿站值班时长待审批，请及时处理！", "驿站值班时长消息","stadiumCard_consumptionHistory"),
    UAC_USER_RECHARGE("您有一条赛事活动补卡待审批，请及时处理！", "赛事活动补卡消息","stadiumCard_consumptionHistory"),
    UAC_USER_RECHARGE("您有一条赛事活动时长待审批，请及时处理！", "赛事活动时长消息","stadiumCard_consumptionHistory"),
    UAC_USER_RECHARGE("您有一条指导员认证信息待审批，请及时出来！", "指导员认证消息","stadiumCard_consumptionHistory"),

    UAC_USER_RECHARGE("您有一条赛事活动时长待审批，请及时处理！", "赛事活动时长消息","stadiumCard_consumptionHistory");
*/
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
