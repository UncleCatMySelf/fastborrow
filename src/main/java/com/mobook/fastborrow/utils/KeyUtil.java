package com.mobook.fastborrow.utils;

import com.mobook.fastborrow.constant.UtilConstant;

import java.util.Random;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 17:18 2018\6\30 0030
 */
public class KeyUtil {
    /**
     * 生成唯一的主键
     * 格式：时间+随机数
     * 在并发下任然可能重复，需要加synchronized 多线程
     * @return
     */
    public static synchronized String genUniqueKey() {
        Random random = new Random();
        //生成6位随机数
        Integer number = random.nextInt(900000) + 100000;

        return System.currentTimeMillis() + String.valueOf(number);
    }

    public static synchronized String getMobookKey(Integer tagNum){
        Random random = new Random();
        //生成6为随机数
        Integer number = random.nextInt(900000) + 100000;
        return UtilConstant.MOBOOKID_REFFIX+"0"+String.valueOf(tagNum)+String.valueOf(number);
    }
}
