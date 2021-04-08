package com.herocheer.instructor.enums;

public enum JobTitleEnums {
    ordinary_member(0,"普通成员"),
    secretary_General(1,"秘书长"),
    vice_president(2,"副会长"),
    president(3,"会长");


    private int type;
    private String name;

    JobTitleEnums(int type, String name) {
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
        JobTitleEnums[] array = values();
        for (JobTitleEnums en : array) {
            if (en.getName().equals(name)) {
                return en.getType();
            }
        }
        return ordinary_member.getType();
    }
}
