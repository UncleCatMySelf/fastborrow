package com.mobook.fastborrow.service;

import com.mobook.fastborrow.dataobject.Logistics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 14:47 2018\7\19 0019
 */
public interface LogisticsService {

    Logistics save(Logistics logistics);

    List<Logistics> findAll();

    Page<Logistics> findAll(Pageable pageable);

    Logistics findOne(Integer logId);

    List<Logistics> findByUserId(Integer userId);

    Page<Logistics> findByUserId(Integer userId, Pageable pageable);

    void deleteOne(Integer logId);

    Logistics findByUserIdAndStatus(Integer userId, Integer status);

}
