package com.mobook.fastborrow.service;

import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;
import com.mobook.fastborrow.dto.OrderDTO;
import com.mobook.fastborrow.wechatpay.WxResponse;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 17:35 2018\7\27 0027
 */
public interface PayService {

    WxResponse create(OrderDTO orderDTO);

    PayResponse notify(String notifyData);

    RefundResponse refund(OrderDTO orderDTO);

}
