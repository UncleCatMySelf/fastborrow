package com.mobook.fastborrow.service;

import com.mobook.fastborrow.dataobject.BookMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 15:09 2018\6\30 0030
 */
public interface BookMessageService {

    BookMessage save(BookMessage bookMessage);

    Page<BookMessage> findAll(Pageable pageable);

    BookMessage findOne(String mobookId);

    Page<BookMessage> findByMobookIdAndBookName(String mobookId, String bookName, PageRequest request);

    Page<BookMessage> findByBookName(String bookName, PageRequest request);

    Page<BookMessage> findByMobookId(String mobookId, PageRequest request);

    List<BookMessage> findByIsbn(String isbn);

    BookMessage onSale(String mobookId);

    BookMessage offSale(String mobookId);

}
