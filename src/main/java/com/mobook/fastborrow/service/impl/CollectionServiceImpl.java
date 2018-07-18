package com.mobook.fastborrow.service.impl;

import com.mobook.fastborrow.dataobject.Collection;
import com.mobook.fastborrow.repository.CollectionRepository;
import com.mobook.fastborrow.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 11:36 2018\7\18 0018
 */
@Service
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    private CollectionRepository repository;

    @Override
    public Collection findByUserIdAndIsbn(Integer userId, String isbn, Integer colStatus) {
        return repository.findByUserIdAndIsbnAndColStatus(userId, isbn,colStatus);
    }

    @Override
    public Collection save(Collection collection) {
        return repository.save(collection);
    }
}
