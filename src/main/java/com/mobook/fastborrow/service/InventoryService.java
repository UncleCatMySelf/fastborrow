package com.mobook.fastborrow.service;

import com.mobook.fastborrow.dataobject.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;


/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 15:51 2018\7\2 0002
 */
public interface InventoryService {

    Inventory save(Inventory inventory);

    Inventory findByMobookId(String mobookId);

    Page<Inventory> findAll(Pageable pageable);

    Inventory findOne(Integer id);

    List<Inventory> findByIsbn(String isbn);

    List<Inventory> findByWhereTag(String whereTag);
}
