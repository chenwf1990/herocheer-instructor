package com.herocheer.instructor.enums;

/**
 * @author chenwf
 * @desc 新闻活动审核状态枚举
 * @date 2021/1/5
 * @company 厦门熙重电子科技有限公司
 */
public enum NewsAuditStateEnums {
    to_audit(0,"待审核"),
    to_reject(1,"驳回"),
    to_pass(2,"发布"),
    to_backout(3,"撤销");
    NewsAuditStateEnums(){

    }

    private int state;
    private String name;

    NewsAuditStateEnums(int state, String name) {

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
