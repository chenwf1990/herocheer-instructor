package com.herocheer.instructor.enums;

public enum SignType {
    SIGN_IN(1,"签到"),
    SIGN_OUT(2,"签退");


    private int type;
    private String name;

    SignType(int type, String name) {
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
