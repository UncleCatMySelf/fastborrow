package com.mobook.fastborrow.repository;

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
 * @Date:Created in 20:45 2018\6\12 0012
 */
public class BackUserRepositoryTest extends FastborrowApplicationTests {

    @Autowired
    private BackUserRepository repository;

    @Test
    public void findByBuName() throws Exception {
        BackUser backUser = repository.findByBuName("admin");
        Assert.assertEquals("admin",backUser.getBuPassword());
    }

}