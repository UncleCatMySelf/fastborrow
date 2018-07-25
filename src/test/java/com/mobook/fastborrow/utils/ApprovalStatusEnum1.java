package com.mobook.fastborrow.utils;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 14:03 2018\7\24 0024
 */
public enum ApprovalStatusEnum1 {
    PASS("PASS", "通过"),

    REJECT("REJECT", "退回"),

    WAIT("WAIT", "待审核");

    private String name ;

    private String label;

    ApprovalStatusEnum1(String name, String label) {
        this.name = name;
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
