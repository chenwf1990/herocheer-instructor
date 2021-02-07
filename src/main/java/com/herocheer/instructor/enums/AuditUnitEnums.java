package com.herocheer.instructor.enums;

/**
 * @author chenwf
 * @desc 审批单位
 * @date 2021/1/13
 * @company 厦门熙重电子科技有限公司
 */
public enum AuditUnitEnums {
    OTHER("WLJ_OTHER", "其他","WLJ_TYJ"),
    HLQ("WLJ_HLQ", "湖里区文旅局","WLJ_HLQ"),
    SMQ("WLJ_SMQ", "思明区文旅局","WLJ_SMQ"),
    JMQ("WLJ_JMQ", "集美区文旅局","WLJ_JMQ"),
    HCQ("WLJ_HCQ", "海沧区文旅局","WLJ_HCQ"),
    XAQ("WLJ_XAQ", "翔安区文旅局","WLJ_XAQ"),
    TAQ("WLJ_TAQ", "同安区文旅局","WLJ_TAQ"),
    TYJ("WLJ_TYJ", "体育局","WLJ_TYJ"),
    NEWS_AUDIT("NEWS_AUDIT", "新闻审批","NEWS_AUDIT");

    private String code;
    private String name;
    private String roleCode;

    AuditUnitEnums(String code, String name,String roleCode) {
        this.code = code;
        this.name = name;
        this.roleCode = roleCode;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getType(String name) {
        AuditUnitEnums[] array = values();
        for (AuditUnitEnums en : array) {
            if (en.getName().equals(name)) {
                return en.getCode();
            }
        }
        return null;
    }

    public static String getName(String code) {
        AuditUnitEnums[] array = values();
        for (AuditUnitEnums en : array) {
            if (en.getCode().equals(code)) {
                return en.getName();
            }
        }
        return null;
    }

    public static String getRoleCode(String code) {
        AuditUnitEnums[] array = values();
        for (AuditUnitEnums en : array) {
            if (en.getCode().equals(code)) {
                return en.getRoleCode();
            }
        }
        return null;
    }
}
