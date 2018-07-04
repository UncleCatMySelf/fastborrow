package com.mobook.fastborrow.service;

import com.mobook.fastborrow.dataobject.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 15:51 2018\7\2 0002
 */
public interface InventoryService {

    Inventory save(Inventory inventory);

    Page<Inventory> findAll(Pageable pageable);

    Inventory findOne(Integer id);

    Inventory findByIsbn(String isbn);

    Page<Inventory> findByIsbnISLikeAndBookNameISLike(String isbn, String bookName, PageRequest request);

    Page<Inventory> findByBookNameISLike(String bookName, PageRequest request);

    Page<Inventory> findByIsbnISLike(String isbn, PageRequest request);
}
