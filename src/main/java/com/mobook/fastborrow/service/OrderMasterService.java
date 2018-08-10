package com.mobook.fastborrow.service;

import com.mobook.fastborrow.dataobject.OrderMaster;
import com.mobook.fastborrow.dataobject.User;
import com.mobook.fastborrow.dto.OrderDTO;
import org.hibernate.criterion.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 10:58 2018\7\27 0027
 */
public interface OrderMasterService {

    OrderMaster findByBuyerOpenid (String buyerOpenid);

    List<OrderMaster> findByBuyerOpenidAndOrderStatus(String buyerOpenid,Integer orderStatus);

    OrderMaster findByBuyerOpenidAndOrderStatusIsNot(String buyerOpenid,Integer orderStatus);

    Page<OrderMaster> findByOrderStatus(Integer orderStatus, Pageable pageable);

    OrderMaster save(OrderMaster orderMaster);

    List<OrderMaster> findByBuyerOpenidAndOrderStatusList(String buyerOpenid,Integer orderStatus);

    OrderDTO findOne(String orderId);

    OrderMaster getOne(String orderId);

    OrderDTO paid(OrderDTO orderDTO);

    OrderDTO createDeposit(String openId);

    OrderDTO findByBuyerOpenidToDeposit(String openId);

    OrderMaster createBuy(User user, String[] isbns,String renew,String note);
}
