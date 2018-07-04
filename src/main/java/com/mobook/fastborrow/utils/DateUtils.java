package com.mobook.fastborrow.utils;

import java.text.DateFormat;
import java.util.Date;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 22:09 2018\6\12 0012
 */
public class DateUtils {
    public static String getSystemDate(){
        Date date = new Date();
        DateFormat format = DateFormat.getDateTimeInstance();
        return format.format(date);
    }
}
