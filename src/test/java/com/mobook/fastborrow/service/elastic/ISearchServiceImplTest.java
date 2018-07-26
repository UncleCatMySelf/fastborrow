package com.mobook.fastborrow.service.elastic;

import com.mobook.fastborrow.FastborrowApplicationTests;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 15:44 2018\7\25 0025
 */
public class ISearchServiceImplTest extends FastborrowApplicationTests {

    @Autowired
    private ISearchService searchService;

    @Test
    public void index() throws Exception {
        searchService.index("2345234");
    }

    @Test
    public void remove() throws Exception {
        searchService.remove("2345234");
    }

}