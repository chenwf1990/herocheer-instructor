package com.herocheer.instructor.enums;

/**
 * @author chenwf
 * @desc 补卡类型枚举
 * @date 2021/1/5
 * @company 厦门熙重电子科技有限公司
 */
public enum ReissueCardEnums {
    YES(0,"正常打卡"),
    NO(1,"补卡");

    private int state;
    private String name;

    ReissueCardEnums(int state, String name) {
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
