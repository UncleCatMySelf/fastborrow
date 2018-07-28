package com.mobook.fastborrow.service.impl;

import com.mobook.fastborrow.dataobject.OrderDetail;
import com.mobook.fastborrow.dataobject.OrderMaster;
import com.mobook.fastborrow.dto.OrderDTO;
import com.mobook.fastborrow.enums.OrderStatusEnum;
import com.mobook.fastborrow.enums.PayStatusEnum;
import com.mobook.fastborrow.enums.ResultEnum;
import com.mobook.fastborrow.exception.FastBorrowException;
import com.mobook.fastborrow.repository.OrderDetailRepository;
import com.mobook.fastborrow.repository.OrderMasterRepository;
import com.mobook.fastborrow.service.OrderMasterService;
import com.mobook.fastborrow.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 10:58 2018\7\27 0027
 */
@Service
@Slf4j
public class OrderMasterServiceImpl implements OrderMasterService {

    @Autowired
    private OrderMasterRepository repository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public OrderMaster findByBuyerOpenid(String buyerOpenid) {
        return repository.findByBuyerOpenid(buyerOpenid);
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = repository.findById(orderId).get();
        if (orderMaster == null){
            throw new FastBorrowException(ResultEnum.ORDER_NOT_EXIST);
        }

        List<OrderDetail> orderDetailList = new ArrayList<>();
        if (orderMaster.getOrderNum() == 0){//押金
            orderDetailList = null;
        }else{
            orderDetailList = orderDetailRepository.findByOrderId(orderId);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.PENDPAY.getCode())){
            log.error("【订单支付成功】订单状态不正确，orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new FastBorrowException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("【订单支付完成】订单支付状态不正确，orderDTO={}",orderDTO);
            throw new FastBorrowException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        //修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = repository.findById(orderDTO.getOrderId()).get();
        orderMaster.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster updateResult = repository.save(orderMaster);
        if (updateResult == null){
            log.error("【订单支付完成】更新失败，orderMaster={}",orderMaster);
            throw new FastBorrowException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO createDeposit(String openId) {
        Date date = new Date();
        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        String deposit_null = "*";
        OrderDTO orderDTO = new OrderDTO(orderId,deposit_null,deposit_null,deposit_null,deposit_null,openId,
                new BigDecimal(0.01).add(orderAmount),null, OrderStatusEnum.PENDPAY.getCode(), PayStatusEnum.WAIT.getCode(),
                date,date);
        OrderMaster orderMaster = new OrderMaster(orderId,deposit_null,deposit_null,deposit_null,deposit_null,openId,
                null,new BigDecimal(0.01).add(orderAmount),null,new BigDecimal(0.01).add(orderAmount),
                0,0,OrderStatusEnum.PENDPAY.getCode(), PayStatusEnum.WAIT.getCode(),date,date);
        repository.save(orderMaster);
        return orderDTO;
    }

}
