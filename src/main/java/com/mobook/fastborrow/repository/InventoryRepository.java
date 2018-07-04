package com.mobook.fastborrow.repository;

import com.mobook.fastborrow.dataobject.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 17:06 2018\6\27 0027
 */
public interface InventoryRepository extends JpaRepository<Inventory,Integer> {

    Inventory findByIsbn(String isbn);

    Page<Inventory> findByIsbnIsLike(@Param("isbn") String isbn,Pageable pageable);

    Page<Inventory> findByBookNameIsLike(@Param("bookName") String bookName, Pageable pageable);

    Page<Inventory> findByIsbnIsLikeAndBookNameIsLike(@Param("isbn") String isbn,
                                                      @Param("bookName") String bookName,
                                                      Pageable pageable);
}
