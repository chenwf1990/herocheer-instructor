package com.herocheer.instructor.enums;

/**
 * 项目类型
 *
 * @author gaorh
 * @create 2021-06-08
 */
public enum ItemTypeEnums {

    NEWS(1,"活动新闻"),
    COURSE(2,"公益课程"),
    VIDEO (3,"健身视频");

    private Integer code;
    private String name;

    ItemTypeEnums(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
