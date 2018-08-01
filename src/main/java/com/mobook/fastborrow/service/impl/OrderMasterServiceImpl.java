package com.mobook.fastborrow.service.impl;

import com.mobook.fastborrow.dataobject.*;
import com.mobook.fastborrow.dto.OrderDTO;
import com.mobook.fastborrow.enums.*;
import com.mobook.fastborrow.exception.FastBorrowException;
import com.mobook.fastborrow.repository.*;
import com.mobook.fastborrow.service.OrderMasterService;
import com.mobook.fastborrow.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    @Autowired
    private LogisticsRepository logisticsRepository;
    @Autowired
    private BookMessageRepository bookMessageRepository;
    @Autowired
    private InventoryRepository inventoryRepository;

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

    @Override
    public OrderDTO findByBuyerOpenidToDeposit(String openId) {
        OrderDTO orderDTO = new OrderDTO();
        Sort sort = new Sort(Sort.Direction.DESC,"createTime");
        Pageable pageable = new PageRequest(0,5,sort);
        Page<OrderMaster> orderMasters = repository.findByBuyerOpenidAndExpressNum(openId,"*",pageable);
        OrderMaster orderMaster = orderMasters.getContent().get(0);
        BeanUtils.copyProperties(orderMaster,orderDTO);
        return orderDTO;
    }

    @Override
    public OrderMaster createBuy(User user, String[] isbns,String renew,String note) {
        //获取对应用户默认地址
        Logistics logistics = logisticsRepository.findByUserIdAndStatus(user.getUserId(), LogisticsStatusEnum.UP.getCode());
        OrderMaster orderMaster = createOrderMaster(user,logistics,isbns,renew,note);
        return repository.save(orderMaster);
    }

    private OrderMaster createOrderMaster(User user, Logistics logistics, String[] isbns,String renew,String note) {
        String orderId = KeyUtil.genUniqueKey();
        BigDecimal sum = new BigDecimal(0);
        for (String item : isbns){
            BookMessage bookMessage = bookMessageRepository.findById(item).get();
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            orderDetail.setIsbn(bookMessage.getIsbn());
            orderDetail.setMobookId(inventoryRepository.findByIsbnAndStatus(item, InventoryStatusEnum.IN.getCode()).get(0).getMobookId());
            orderDetail.setMobookName(bookMessage.getBookName());
            orderDetail.setMobookIcon(bookMessage.getImages());
            orderDetail.setMobookPrice(bookMessage.getPrice());
            orderDetailRepository.save(orderDetail);
            sum.add(bookMessage.getPrice());
        }
        OrderMaster orderMaster = new OrderMaster();
        //订单号
        orderMaster.setOrderId(orderId);
        //买家地址信息
        orderMaster.setBuyerAddress(logistics.getLogRegional()+","+logistics.getLogAddress());
        //买家名称
        orderMaster.setBuyerName(logistics.getLogName());
        //买家微信openid
        orderMaster.setBuyerOpenid(user.getOpenId());
        //买家手机号
        orderMaster.setBuyerPhone(logistics.getLogPhone());
        //快递单号
        orderMaster.setExpressNum("");
        //订单金额-全部书的金额总价
        orderMaster.setOrderAmount(sum);
        //订单押金
        orderMaster.setOrderDeposit(new BigDecimal(99));
        //快递费用
        orderMaster.setOrderExpress(new BigDecimal(10));
        //子订单数量
        orderMaster.setOrderNum(isbns.length);
        //实际付款-折扣后
        orderMaster.setOrderPayment(calculateCosts(renew,sum));
        //续借期限
        orderMaster.setOrderTime(Integer.parseInt(renew));
        //支付状态
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        //订单状态
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        return orderMaster;
    }

    private BigDecimal calculateCosts(String renew, BigDecimal sum) {
        if ("1".equals(renew)){//三个月
            return sum.multiply(BigDecimal.valueOf(0.03)).add(new BigDecimal(10));
        }else if("2".equals(renew)){//半年
            return sum.multiply(BigDecimal.valueOf(0.07)).add(new BigDecimal(10));
        }else if("3".equals(renew)){//一年
            return sum.multiply(BigDecimal.valueOf(0.1)).add(new BigDecimal(10));
        }else{
            throw new FastBorrowException(ResultEnum.ORDER_UPDATE_FAIL);
        }
    }

}
