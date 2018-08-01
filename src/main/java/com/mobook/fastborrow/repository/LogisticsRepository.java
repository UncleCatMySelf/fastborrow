package com.mobook.fastborrow.repository;

import com.mobook.fastborrow.dataobject.Logistics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 17:06 2018\6\27 0027
 */
public interface LogisticsRepository extends JpaRepository<Logistics, Integer> {

    List<Logistics> findByUserId(Integer userId);

    Page<Logistics> findByUserId(Integer userId, Pageable pageable);

    Logistics findByUserIdAndStatus(Integer userId, Integer status);
}
