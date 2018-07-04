package com.mobook.fastborrow.repository;

import com.mobook.fastborrow.dataobject.BookMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.awt.print.Book;
import java.util.List;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 16:57 2018\6\27 0027
 */
public interface BookMessageRepository extends JpaRepository<BookMessage, String> {

    List<BookMessage> findByIsbn(String isbn);

    List<BookMessage> findByStatus(Integer status);

    Page<BookMessage> findByStatus(Integer status,Pageable pageable);

    Page<BookMessage> findByMobookIdIsLike(@Param("mobookId") String mobookId, Pageable pageable);

    Page<BookMessage> findByMobookIdIsLikeAndStatus(@Param("mobookId") String mobookId,@Param("status") Integer status ,Pageable pageable);

    Page<BookMessage> findByBookNameIsLike(@Param("bookName") String bookName, Pageable pageable);

    Page<BookMessage> findByBookNameIsLikeAndStatus(@Param("bookName") String bookName,@Param("status") Integer status , Pageable pageable);

    Page<BookMessage> findByMobookIdIsLikeAndBookNameIsLike(@Param("mobookId") String mobookId,
                                                            @Param("bookName") String bookName,
                                                            Pageable pageable);

    Page<BookMessage> findByMobookIdIsLikeAndBookNameIsLikeAndStatus(@Param("mobookId") String mobookId,
                                                            @Param("bookName") String bookName,
                                                            @Param("status") Integer status,
                                                            Pageable pageable);

}
