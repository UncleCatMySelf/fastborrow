package com.mobook.fastborrow.utils;

import com.mobook.fastborrow.enums.CodeEnum;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 14:28 2018\6\16 0016
 */
public class EnumUtil {

    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass){
        for (T each : enumClass.getEnumConstants()){
            if (code.compareTo((Integer) each.getCode()) == 0){
                return each;
            }
        }
        return null;
    }

}
