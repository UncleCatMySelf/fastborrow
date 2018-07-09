package com.mobook.fastborrow.service.impl;

import com.mobook.fastborrow.dataobject.HotKey;
import com.mobook.fastborrow.repository.HotKeyRepository;
import com.mobook.fastborrow.service.HotKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 14:55 2018\7\9 0009
 */
@Service
public class HotKeyServiceImpl implements HotKeyService {

    @Autowired
    private HotKeyRepository repository;

    @Override
    public HotKey findOne(Integer id) {
        return repository.findById(id).get();
    }

    @Override
    public List<HotKey> findAll() {
        return repository.findAll();
    }

    @Override
    public HotKey save(HotKey hotKey) {
        return repository.save(hotKey);
    }
}
