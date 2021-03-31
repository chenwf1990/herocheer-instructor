package com.herocheer.instructor.enums;

/**
 * @author linjf
 * @desc 签到状态枚举
 * @date 2021/1/7
 * @company 厦门熙重电子科技有限公司
 * 0.正常签到 1.异常签到 2.待完成
 */
public enum SignStatusEnums {
    SIGN_NORMAL(0,"正常"),
    SIGN_ABNORMAL(1,"异常"),
    SIGN_UN_FINISH(2,"待完成"),

    SIGN_DO(0,"未签到"),
    SIGN_DONE(1,"已签到"),
    SIGN_FAIED(2,"签到失败");

    private int status;
    private String name;

    SignStatusEnums(int status, String name) {
        this.status = status;
        this.name = name;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
