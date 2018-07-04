package com.mobook.fastborrow.utils;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 10:50 2018\6\13 0013
 */
public class LogUtil {

    public static String formatLogMessage(String logConstant,String logMsgConstant){
        return logConstant+":"+logMsgConstant+"-"+DateUtils.getSystemDate();
    }

}
