package com.mobook.fastborrow.service.impl;

import com.mobook.fastborrow.dataobject.Inventory;
import com.mobook.fastborrow.repository.InventoryRepository;
import com.mobook.fastborrow.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 15:56 2018\7\2 0002
 */
@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository repository;

    @Override
    public Inventory save(Inventory inventory) {
        return repository.save(inventory);
    }

    @Override
    public Page<Inventory> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Inventory findOne(Integer id) {
        return repository.findById(id).get();
    }

    @Override
    public List<Inventory> findByIsbn(String isbn) {
        return repository.findByIsbn(isbn);
    }

    @Override
    public List<Inventory> findByWhereTag(String whereTag) {
        return repository.findByWhereTag(whereTag);
    }


}
