package com.mobook.fastborrow.utils;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 14:03 2018\7\24 0024
 */
public class TestEnum {
    public static <T extends Enum> T test(Class<T> clazz){
//      xxxxxxxxxxxxx
        return null;
    }

    public static <T extends java.lang.Enum> T testLnag(Class<T> clazz){
//      xxxxxxxxxxxxx
        return null;
    }

    public static void main(String[] args) {
        testLnag(ApprovalStatusEnum.class);
        testLnag(ApprovalStatusEnum1.class);
    }
}
