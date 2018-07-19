package com.mobook.fastborrow.service.impl;

import com.mobook.fastborrow.dataobject.Logistics;
import com.mobook.fastborrow.repository.LogisticsRepository;
import com.mobook.fastborrow.service.LogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Logistics findOne(Integer logId) {
        return repository.findById(logId).get();
    }

    @Override
    public List<Logistics> findByUserId(Integer userId) {
        return repository.findByUserId(userId);
    }
}
