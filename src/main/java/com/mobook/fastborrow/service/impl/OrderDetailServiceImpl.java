package com.mobook.fastborrow.service.impl;

import com.mobook.fastborrow.dataobject.OrderDetail;
import com.mobook.fastborrow.repository.OrderDetailRepository;
import com.mobook.fastborrow.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 18:02 2018\8\8 0008
 */
@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepository repository;

    @Override
    public List<OrderDetail> findByOrderId(String orderId) {
        return repository.findByOrderId(orderId);
    }
}
