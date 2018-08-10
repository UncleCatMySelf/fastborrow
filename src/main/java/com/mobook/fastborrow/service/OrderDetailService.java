package com.mobook.fastborrow.service;

import com.mobook.fastborrow.dataobject.OrderDetail;

import java.util.List;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 18:01 2018\8\8 0008
 */
public interface OrderDetailService {

    List<OrderDetail> findByOrderId(String orderId);

}
