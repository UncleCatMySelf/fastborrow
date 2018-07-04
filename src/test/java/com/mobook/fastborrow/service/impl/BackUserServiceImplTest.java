package com.mobook.fastborrow.service.impl;

import com.mobook.fastborrow.FastborrowApplicationTests;
import com.mobook.fastborrow.dataobject.BackUser;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 21:10 2018\6\12 0012
 */
public class BackUserServiceImplTest extends FastborrowApplicationTests {

    @Autowired
    private BackUserServiceImpl backUserService;

    @Test
    public void findBackUserInfoByBuName() throws Exception {
        BackUser backUser = backUserService.findBackUserInfoByBuName("admin");
        Assert.assertEquals("admin",backUser.getBuPassword());
    }

}