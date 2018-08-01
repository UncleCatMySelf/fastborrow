package com.mobook.fastborrow.service.impl;

import com.mobook.fastborrow.dataobject.Logistics;
import com.mobook.fastborrow.repository.LogisticsRepository;
import com.mobook.fastborrow.service.LogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 14:47 2018\7\19 0019
 */
@Service
public class LogisticsServiceImpl implements LogisticsService {

    @Autowired
    private LogisticsRepository repository;


    @Override
    public Logistics save(Logistics logistics) {
        return repository.save(logistics);
    }

    @Override
    public List<Logistics> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<Logistics> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Logistics findOne(Integer logId) {
        return repository.findById(logId).get();
    }

    @Override
    public List<Logistics> findByUserId(Integer userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public Page<Logistics> findByUserId(Integer userId, Pageable pageable) {
        return repository.findByUserId(userId, pageable);
    }

    @Override
    public void deleteOne(Integer logId) {
        repository.deleteById(logId);
    }

    @Override
    public Logistics findByUserIdAndStatus(Integer userId, Integer status) {
        return repository.findByUserIdAndStatus(userId,status);
    }
}
