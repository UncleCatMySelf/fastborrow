package com.mobook.fastborrow.service;

import com.mobook.fastborrow.dataobject.BookMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

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

    List<BookMessage> findByWhereTag(String whereTag);

    List<BookMessage> findByTagNum(Integer tagNum);

    List<BookMessage> findByRecNum(Integer recNum);

    Page<BookMessage> findByStatusIsNotAAndTagNum(Integer status,Integer tagNum,Pageable pageable);

    Page<BookMessage> findByStatusIsNotAndRecNumIs(Integer status,Pageable pageable);

    Page<BookMessage> findByMobookIdAndBookName(String mobookId, String bookName, PageRequest request);

    Page<BookMessage> findByBookName(String bookName, PageRequest request);

    Page<BookMessage> findByMobookId(String mobookId, PageRequest request);

    Page<BookMessage> findByMobookIdIsLikeAndStatus(String mobookId,Integer status ,Pageable pageable);

    Page<BookMessage> findByMobookIdIsLikeAndStatusIsNot(String mobookId,Integer status , Pageable pageable);

    Page<BookMessage> findByBookNameIsLikeAndStatus(String bookName,Integer status , Pageable pageable);

    Page<BookMessage> findByBookNameIsLikeAndStatusIsNot(String bookName,Integer status , Pageable pageable);

    Page<BookMessage> findByMobookIdIsLikeAndBookNameIsLikeAndStatus(String mobookId,
                                                                     String bookName,
                                                                     Integer status,
                                                                     Pageable pageable);
    Page<BookMessage> findByMobookIdIsLikeAndBookNameIsLikeAndStatusIsNot(String mobookId,
                                                                          String bookName,
                                                                          Integer status,
                                                                          Pageable pageable);

    Page<BookMessage> findByStatus(Integer status,Pageable pageable);

    List<BookMessage> findByIsbn(String isbn);

    List<BookMessage> findByStatus(Integer status);

    BookMessage onSale(String mobookId);

    BookMessage offSale(String mobookId);

}
