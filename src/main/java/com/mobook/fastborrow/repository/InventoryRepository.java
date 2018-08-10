package com.mobook.fastborrow.repository;

import com.mobook.fastborrow.dataobject.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 17:06 2018\6\27 0027
 */
public interface InventoryRepository extends JpaRepository<Inventory,Integer> {

    List<Inventory> findByIsbn(String isbn);

    Inventory findByMobookId(String mobookId);

    Page<Inventory> findByIsbnIsLike(@Param("isbn") String isbn,Pageable pageable);

    List<Inventory> findByWhereTag(String whereTag);

    List<Inventory> findByIsbnAndStatus(String isbn,Integer status);
}
