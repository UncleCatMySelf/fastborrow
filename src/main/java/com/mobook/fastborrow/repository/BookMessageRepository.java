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

    Integer countByIsbn(String isbn);

//    List<BookMessage> findByWhereTag(String whereTag);

    List<BookMessage> findByTagNum(Integer tagNum);

    List<BookMessage> findByStatus(Integer status);

    List<BookMessage> findByRecNum(Integer recNum);

    Page<BookMessage> findByStatusIsNotAndRecNumIs(@Param("status") Integer status,@Param("recNum") Integer recNum,Pageable pageable);

    Page<BookMessage> findByStatus(Integer status,Pageable pageable);

    Page<BookMessage> findByStatusIsNotAndTagNum(@Param("status") Integer status,@Param("tagNum") Integer tagNum,Pageable pageable);

    Page<BookMessage> findByIsbnIsLike(@Param("isbn") String isbn, Pageable pageable);

    Page<BookMessage> findByIsbnIsLikeAndStatusIsNotAndRecNumIs(@Param("isbn") String isbn,@Param("status") Integer status,@Param("recNum") Integer recNum,Pageable pageable);

    Page<BookMessage> findByIsbnIsLikeAndStatus(@Param("isbn") String isbn,@Param("status") Integer status ,Pageable pageable);

    Page<BookMessage> findByBookNameIsLike(@Param("bookName") String bookName, Pageable pageable);

    Page<BookMessage> findByBookNameIsLikeAndStatus(@Param("bookName") String bookName,@Param("status") Integer status , Pageable pageable);

    Page<BookMessage> findByBookNameIsLikeAndStatusIsNotAndRecNumIs(@Param("bookName") String bookName,@Param("status") Integer status ,@Param("recNum") Integer recNum, Pageable pageable);

    Page<BookMessage> findByIsbnIsLikeAndBookNameIsLike(@Param("isbn") String isbn,
                                                            @Param("bookName") String bookName,
                                                            Pageable pageable);

    Page<BookMessage> findByIsbnIsLikeAndBookNameIsLikeAndStatus(@Param("isbn") String isbn,
                                                            @Param("bookName") String bookName,
                                                            @Param("status") Integer status,
                                                            Pageable pageable);

    Page<BookMessage> findByIsbnIsLikeAndBookNameIsLikeAndStatusIsNotAndRecNumIs(@Param("isbn") String isbn,
                                                                     @Param("bookName") String bookName,
                                                                     @Param("status") Integer status,
                                                                     @Param("recNum") Integer recNum,
                                                                     Pageable pageable);

    List<BookMessage> findByBookNameIsLikeAndStatusIsNot(@Param("bookName") String bookName,
                                                                         @Param("status") Integer status);

    List<BookMessage> findByInfoIsLikeAndStatusIsNot(@Param("info") String info,
                                                     @Param("status") Integer status);

    List<BookMessage> findByAuthorIsLikeAndStatusIsNot(@Param("author") String author,
                                                       @Param("status") Integer status);
}
