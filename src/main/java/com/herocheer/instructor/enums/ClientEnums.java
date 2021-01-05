package com.herocheer.instructor.enums;

/**
 * @author chenwf
 * @desc
 * @date 2021/1/5
 * @company 厦门熙重电子科技有限公司
 */
public enum ClientEnums {
    pc(0,"pc端"),
    h5(1,"H5"),
    xcx(2,"小程序"),
    ios(3,"ios"),
    android(4,"安卓");


    private int type;
    private String name;

    ClientEnums(int type, String name) {
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
}
