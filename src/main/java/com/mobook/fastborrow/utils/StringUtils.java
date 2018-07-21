package com.mobook.fastborrow.utils;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 13:50 2018\7\20 0020
 */
public class StringUtils {

    public static Integer getStringSearchSize(String msg){
        String[] strs = msg.split(",");
        return strs.length;
    }

    public static String deleteOne(String msg){
        return org.apache.commons.lang3.StringUtils.substringAfter(msg,",");
    }

    public static boolean varity(String userSearchValue,String msg){
        String[] strs = userSearchValue.split(",");
        if (userSearchValue.equals(msg)){
            return false;
        }
        for (int i = 0; i < strs.length-1;i++){
            if (strs[i].equals(msg)){
                return false;
            }
        }
        return true;
    }

}
