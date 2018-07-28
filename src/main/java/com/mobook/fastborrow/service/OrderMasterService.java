package com.mobook.fastborrow.service;

import com.mobook.fastborrow.dataobject.OrderMaster;
import com.mobook.fastborrow.dto.OrderDTO;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 10:58 2018\7\27 0027
 */
public interface OrderMasterService {

    OrderMaster findByBuyerOpenid (String buyerOpenid);

    OrderDTO findOne(String orderId);

    OrderDTO paid(OrderDTO orderDTO);

    OrderDTO createDeposit(String openId);
}
