package com.mobook.fastborrow.repository;

import com.mobook.fastborrow.FastborrowApplicationTests;
import com.mobook.fastborrow.dataobject.BookMessage;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.Assert.*;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 11:38 2018\7\2 0002
 */
public class BookMessageRepositoryTest extends FastborrowApplicationTests {

    @Autowired
    private BookMessageRepository bookMessageRepository;

    @Test
    public void Test() throws Exception {
//        Pageable pageable = new PageRequest(0,3);
//        Page<BookMessage> bookMessagePage = bookMessageRepository.findByMobookIdIsLike("%780%",pageable);
//        Page<BookMessage> bookMessagePage = bookMessageRepository.findByBookNameIsLike("%ce%",pageable);
//        Page<BookMessage> bookMessagePage = bookMessageRepository.findByMobookIdIsLikeAndBookNameIsLike("%780%","%c%",pageable);
//        Assert.assertNotEquals(0,bookMessagePage.getTotalElements());
        String isbn = "2345nkl";
//        BookMessage bookMessage = bookMessageRepository.findByIsbn(isbn);
//        Assert.assertEquals("ceshiss",bookMessage.getBookName());

    }

}