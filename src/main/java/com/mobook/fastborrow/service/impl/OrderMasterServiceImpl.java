package com.mobook.fastborrow.service.impl;

import com.mobook.fastborrow.dataobject.OrderMaster;
import com.mobook.fastborrow.repository.OrderMasterRepository;
import com.mobook.fastborrow.service.OrderMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 10:58 2018\7\27 0027
 */
@Service
public class OrderMasterServiceImpl implements OrderMasterService {

    @Autowired
    private OrderMasterRepository repository;

    @Override
    public OrderMaster findByBuyerOpenid(String buyerOpenid) {
        return repository.findByBuyerOpenid(buyerOpenid);
    }

}
