package com.mobook.fastborrow.service.impl;

import com.mobook.fastborrow.dataobject.Recommended;
import com.mobook.fastborrow.repository.RecommendedRepository;
import com.mobook.fastborrow.service.RecommendedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 15:34 2018\7\10 0010
 */
@Service
public class RecommendedServiceImpl implements RecommendedService {

    @Autowired
    private RecommendedRepository repository;

    @Override
    public Recommended findOne(Integer id) {
        return repository.findById(id).get();
    }

    @Override
    public Page<Recommended> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Recommended save(Recommended recommended) {
        return repository.save(recommended);
    }

    @Override
    public Page<Recommended> findAllByTitleIsLike(String title, Pageable pageable) {
        return repository.findAllByTitleIsLike("%"+title+"%", pageable);
    }

    @Override
    public Page<Recommended> findAllByStatus(Integer status, Pageable pageable) {
        return repository.findAllByStatus(status, pageable);
    }


}
