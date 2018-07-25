package com.mobook.fastborrow.utils;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 13:57 2018\7\24 0024
 */
public enum ApprovalStatusEnum{
    ;
    private static final long serialVersionUID = -502007467073163619L;

    private String label;

    private String name;

    ApprovalStatusEnum(String name, String label) {
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
