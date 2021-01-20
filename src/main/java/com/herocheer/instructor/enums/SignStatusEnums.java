package com.herocheer.instructor.enums;

/**
 * @author linjf
 * @desc 招募信息状态枚举
 * @date 2021/1/7
 * @company 厦门熙重电子科技有限公司
 * 0.待审核1.撤回2.驳回3.招募待启动4.招募中5.招募结束
 */
public enum SignStatusEnums {
    SIGN_NORMAL(0,"正常"),
    SIGN_ABNORMAL(1,"异常");

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
