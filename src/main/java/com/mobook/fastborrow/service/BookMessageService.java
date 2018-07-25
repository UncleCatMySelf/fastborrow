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

    BookMessage findOne(String isbn);

    Integer countByIsbn(String isbn);

//    List<BookMessage> findByWhereTag(String whereTag);

    List<BookMessage> findByTagNum(Integer tagNum);

    List<BookMessage> findByRecNum(Integer recNum);

    Page<BookMessage> findByStatusIsNotAAndTagNum(Integer status,Integer tagNum,Pageable pageable);

    Page<BookMessage> findByStatusIsNotAndRecNumIs(Integer status,Pageable pageable);

    Page<BookMessage> findByIsbnAndBookName(String isbn, String bookName, PageRequest request);

    Page<BookMessage> findByBookName(String bookName, PageRequest request);

    Page<BookMessage> findByIsbn(String isbn, PageRequest request);

    Page<BookMessage> findByIsbnIsLikeAndStatus(String isbn,Integer status ,Pageable pageable);

    Page<BookMessage> findByIsbnIsLikeAndStatusIsNot(String isbn,Integer status , Pageable pageable);

    Page<BookMessage> findByBookNameIsLikeAndStatus(String bookName,Integer status , Pageable pageable);

    Page<BookMessage> findByBookNameIsLikeAndStatusIsNot(String bookName,Integer status , Pageable pageable);

    Page<BookMessage> findByIsbnIsLikeAndBookNameIsLikeAndStatus(String isbn,
                                                                     String bookName,
                                                                     Integer status,
                                                                     Pageable pageable);
    Page<BookMessage> findByIsbnIsLikeAndBookNameIsLikeAndStatusIsNot(String isbn,
                                                                          String bookName,
                                                                          Integer status,
                                                                          Pageable pageable);

    Page<BookMessage> findByStatus(Integer status,Pageable pageable);


    List<BookMessage> findByStatus(Integer status);

    BookMessage onSale(String isbn);

    BookMessage offSale(String isbn);

    List<BookMessage> findByBookNameIsLikeAndStatusIsNot( String bookName,
                                                         Integer status);

    List<BookMessage> findByInfoIsLikeAndStatusIsNot(String info,
                                                      Integer status);

    List<BookMessage> findByAuthorIsLikeAndStatusIsNot( String author,
                                                       Integer status);

}
