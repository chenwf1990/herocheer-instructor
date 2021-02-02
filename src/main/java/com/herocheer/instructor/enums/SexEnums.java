package com.herocheer.instructor.enums;

/**
 * @author chenwf
 * @desc
 * @date 2021/1/5
 * @company 厦门熙重电子科技有限公司
 */
public enum SexEnums {
    unknown(0,"未知"),
    man(1,"男"),
    woman(2,"女");


    private int type;
    private String name;

    SexEnums(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static int getType(String name) {
        SexEnums[] array = values();
        for (SexEnums en : array) {
            if (en.getName().equals(name)) {
                return en.getType();
            }
        }
        return unknown.getType();
    }
}
