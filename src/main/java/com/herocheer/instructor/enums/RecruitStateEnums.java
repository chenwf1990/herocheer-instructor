package com.herocheer.instructor.enums;

/**
 * @author linjf
 * @desc 招募信息状态枚举
 * @date 2021/1/7
 * @company 厦门熙重电子科技有限公司
 * 0.待审核1.撤回2.驳回3.招募待启动4.招募中5.招募结束
 */
public enum RecruitStateEnums {
    PENDING(0,"待审核"),
    TO_RECRUITED(1,"招募待启动"),
    OVERRULE(2,"驳回"),
    WITHDRAW(3,"撤回"),
    RECRUITMENT(4,"招募中"),
    END_RECRUITED(5,"招募结束");
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
