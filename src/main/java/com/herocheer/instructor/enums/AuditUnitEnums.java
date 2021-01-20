package com.herocheer.instructor.enums;

/**
 * @author chenwf
 * @desc
 * @date 2021/1/13
 * @company 厦门熙重电子科技有限公司
 */
public enum AuditUnitEnums {
    OTHER(0, "其他"),
    HLQ(1, "湖里区文旅局"),
    SMQ(2, "思明区文旅局"),
    JMQ(3, "集美区文旅局"),
    HCQ(4, "海沧文旅局"),
    XAQ(5, "翔安文旅局"),
    TAQ(6, "同安文旅局"),
    TYJ(7, "体育局");

    private int type;
    private String name;

    AuditUnitEnums(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int state) {
        this.type = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Integer getType(String name) {
        AuditUnitEnums[] array = values();
        for (AuditUnitEnums en : array) {
            if (en.getName().equals(name)) {
                return en.getType();
            }
        }
        return null;
    }
}
