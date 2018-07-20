package com.mobook.fastborrow.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 15:57 2018\7\20 0020
 */
public class StringUtilsTest {

    String msg = "白,白,人,人,人,人,人";

    @Test
    public void deleteOne() throws Exception {
        System.out.println(StringUtils.deleteOne(msg));
    }

}