package com.mobook.fastborrow.controller.api;

import com.mobook.fastborrow.constant.RedisConstant;
import com.mobook.fastborrow.constant.WXLogMsgConstant;
import com.mobook.fastborrow.dataobject.*;
import com.mobook.fastborrow.dto.Param;
import com.mobook.fastborrow.dto.QueryDTO;
import com.mobook.fastborrow.dto.QueryExDTO;
import com.mobook.fastborrow.enums.LogisticsStatusEnum;
import com.mobook.fastborrow.enums.OrderStatusEnum;
import com.mobook.fastborrow.enums.ResultEnum;
import com.mobook.fastborrow.exception.FastBorrowException;
import com.mobook.fastborrow.service.*;
import com.mobook.fastborrow.utils.ExpressUtil;
import com.mobook.fastborrow.utils.ResultVOUtil;
import com.mobook.fastborrow.vo.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.FastDateFormat;
import org.hibernate.criterion.Order;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 11:07 2018\7\30 0030
 */
@RestController
@RequestMapping("/api/v1/order")
@Slf4j
public class ApiOrderController {

    @Autowired
    private OrderMasterService orderMasterService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private BookMessageService bookMessageService;
    @Autowired
    private LogisticsService logisticsService;

    @PostMapping("/buy")
    public ResultVO buy(@RequestHeader("token") String token, @RequestParam("isbns") String[] isbns,@RequestParam("renew") String renew,
                        @RequestParam("msg") String msg){
        //检查Token并获取token对应的用户id
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.WX_TONEKN_PREFIX,token));
        User user = userService.findOne(Integer.parseInt(tokenValue));
        //0、检查押金是否存在
        if (user.getUserDeposit().compareTo(new BigDecimal(0.0)) == 0){
            return ResultVOUtil.error(WXLogMsgConstant.WX_DEPOSIT_CODE,WXLogMsgConstant.WX_DEPOSIT);
        }else if (user.getUserDeposit().compareTo(new BigDecimal(0.0)) == -1){
            return ResultVOUtil.error(WXLogMsgConstant.WX_DEPOSIT_CODE,WXLogMsgConstant.WX_DEPOSIT);
        }
        //-1、检查二次下单
        List<OrderMaster> orderMasterCheck1 = orderMasterService.findByBuyerOpenidAndOrderStatus(user.getOpenId(), OrderStatusEnum.PENDDELIVERY.getCode());
        if (orderMasterCheck1.size() > 0){
            return ResultVOUtil.error(WXLogMsgConstant.WX_PDOO,WXLogMsgConstant.WX_PDOO_MSG);
        }
        List<OrderMaster> orderMasterCheck2 = orderMasterService.findByBuyerOpenidAndOrderStatus(user.getOpenId(), OrderStatusEnum.NEW.getCode());
        if (orderMasterCheck2.size() > 0){
            return ResultVOUtil.error(WXLogMsgConstant.WX_PDOO,WXLogMsgConstant.WX_PDOO_MSG);
        }
        List<OrderMaster> orderMasterCheck3 = orderMasterService.findByBuyerOpenidAndOrderStatus(user.getOpenId(), OrderStatusEnum.PENDRETURN.getCode());
        if (orderMasterCheck3.size() > 0){
            return ResultVOUtil.error(WXLogMsgConstant.WX_PDOO,WXLogMsgConstant.WX_PDOO_MSG);
        }
        //1、创建订单
        //2、下单
        OrderMaster orderMaster = orderMasterService.createBuy(user,isbns,renew,msg);
        int amount = orderMaster.getOrderAmount().intValue();
        amount = user.getIntegral() + amount;
        user.setIntegral(amount);
        userService.save(user);
        return ResultVOUtil.success(orderMaster.getOrderId());
    }

    @GetMapping("/getLog")
    public ResultVO getLog(@RequestHeader("token") String token){
        //检查Token并获取token对应的用户id
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.WX_TONEKN_PREFIX,token));
        User user = userService.findOne(Integer.parseInt(tokenValue));
        Logistics logistics = logisticsService.findByUserIdAndStatus(user.getUserId(), LogisticsStatusEnum.UP.getCode());
        return ResultVOUtil.success(logistics);
    }


    @GetMapping("/express")
    public ResultVO express(@RequestHeader("token") String token,@RequestParam("orderId") String orderId){
        //检查Token并获取token对应的用户id
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.WX_TONEKN_PREFIX,token));
        User user = userService.findOne(Integer.parseInt(tokenValue));
        OrderMaster orderMaster = orderMasterService.getOne(orderId);
        if (!user.getOpenId().equals(orderMaster.getBuyerOpenid())){
            return ResultVOUtil.error(WXLogMsgConstant.WX_PARAM_CODE,WXLogMsgConstant.WX_PARAM);
        }
        String num = orderMaster.getExpressNum();
        //快递编码
        String code = ExpressUtil.getCode(num);
        Param param = new Param(code,num);
        QueryDTO queryDTO = new QueryDTO();
        try {
            queryDTO = ExpressUtil.getResult(param);
            System.out.println(queryDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(WXLogMsgConstant.WX_PARAM_CODE,WXLogMsgConstant.WX_PARAM);
        }
        return ResultVOUtil.success(queryDTO);
    }

    /**
     * 结算押金判断
     */
    @GetMapping("/sd")
    public ResultVO SettlementDeposit(@RequestHeader("token") String token){
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.WX_TONEKN_PREFIX,token));
        User user = userService.findOne(Integer.parseInt(tokenValue));
        if (user.getUserDeposit().compareTo(new BigDecimal(0.0)) == 0){
            return ResultVOUtil.success(0);
        }else if(user.getUserDeposit().compareTo(new BigDecimal(0.0)) == -1){
            return ResultVOUtil.success(0);
        }else{
            return ResultVOUtil.success(1);
        }
    }


    /**
     * 订单请求
     */
    @GetMapping("/order_list")
    public ResultVO OrderList(@RequestHeader("token") String token,@RequestParam("status") Integer status){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.WX_TONEKN_PREFIX,token));
        User user = userService.findOne(Integer.parseInt(tokenValue));
       // System.out.println(user.toString());
        List<OrderMaster> orderMasterList = orderMasterService.findByBuyerOpenidAndOrderStatus(user.getOpenId(),status);
        List<OrderVO> orderVOS = new ArrayList<>();
        for (OrderMaster item:orderMasterList){
            List<BookOrderVO> bookOrderVOS = new ArrayList<>();
            OrderVO orderVO = new OrderVO();
            orderVO.setAmount(item.getOrderAmount());
            orderVO.setNum(item.getOrderNum());
            orderVO.setOrderId(item.getOrderId());
            orderVO.setTime(formatter.format(item.getCreateTime()));
            orderVO.setStatus(getStatus(item.getOrderStatus()));
            List<OrderDetail> orderDetailList = orderDetailService.findByOrderId(item.getOrderId());
            for(OrderDetail detail:orderDetailList){
                BookOrderVO bookOrderVO = new BookOrderVO();
                BookMessage bookMessage = bookMessageService.findOne(detail.getIsbn());
                BeanUtils.copyProperties(bookMessage,bookOrderVO);
                bookOrderVOS.add(bookOrderVO);
            }
            orderVO.setBookOrderVOList(bookOrderVOS);
            orderVOS.add(orderVO);
        }
        return ResultVOUtil.success(orderVOS);
    }

    private String getStatus(Integer orderStatus) {
        switch (orderStatus){
            case 1:
                return OrderStatusEnum.PENDPAY.getMessage();
            case 2:
                return OrderStatusEnum.PENDDELIVERY.getMessage();
            case 3:
                return OrderStatusEnum.PENDRETURN.getMessage();
            case 4:
                return OrderStatusEnum.COMPLETED.getMessage();
            case 5:
                return OrderStatusEnum.NEW.getMessage();
            default:
                return null;
        }
    }


    /**
     * 已支付后的订单详情
     */
    @GetMapping("/order_detail")
    public ResultVO orderDetail(@RequestHeader("token") String token,@RequestParam("orderId") String orderId){
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.WX_TONEKN_PREFIX,token));
        User user = userService.findOne(Integer.parseInt(tokenValue));
        OrderMaster orderMaster = orderMasterService.getOne(orderId);
        OrderDetailVO orderDetailVO = new OrderDetailVO();
        List<OrderDetailBookVo> orderDetailBookVoList = new ArrayList<>();
        orderDetailVO.setStatus(getStatus(orderMaster.getOrderStatus()));
        orderDetailVO.setLastTime(getListTime(orderMaster.getCreateTime(),orderMaster.getOrderTime()));
        orderDetailVO.setExpressStr(getExpressStr(orderMaster.getExpressNum()));
        orderDetailVO.setLogName(orderMaster.getBuyerName());
        orderDetailVO.setLogPhone(orderMaster.getBuyerPhone());
        orderDetailVO.setLogAddress(orderMaster.getBuyerAddress());
        orderDetailVO.setOrderId(orderMaster.getOrderId());
        orderDetailVO.setOrderDetailBookVoList(getDetailBookVo(orderDetailBookVoList,orderMaster.getOrderId()));
        orderDetailVO.setTime(orderMaster.getOrderTime());
        orderDetailVO.setInvent(String.valueOf(orderMaster.getOrderAmount()));
        orderDetailVO.setNote(orderMaster.getNote());
        orderDetailVO.setNum(orderMaster.getOrderNum());
        orderDetailVO.setAmount(orderMaster.getOrderPayment());
        orderDetailVO.setSum(orderMaster.getOrderAmount());
        return ResultVOUtil.success(orderDetailVO);
    }

    private List<OrderDetailBookVo> getDetailBookVo(List<OrderDetailBookVo> orderDetailBookVoList, String orderId) {
        List<OrderDetail> orderDetailList = orderDetailService.findByOrderId(orderId);
        for (OrderDetail item: orderDetailList){
            OrderDetailBookVo orderDetailBookVo = new OrderDetailBookVo();
            orderDetailBookVo.setIsbn(item.getIsbn());
            orderDetailBookVo.setImage(item.getMobookIcon());
            orderDetailBookVoList.add(orderDetailBookVo);
        }
        return orderDetailBookVoList;
    }

    private QueryExDTO getExpressStr(String expressNum) {
        //快递编码
        String code = ExpressUtil.getCode(expressNum);
        Param param = new Param(code,expressNum);
        QueryDTO queryDTO = new QueryDTO();
        try {
            queryDTO = ExpressUtil.getResult(param);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FastBorrowException(ResultEnum.BOOK_NOT_EXIT);
        }
        return queryDTO.getQueryExDTOList().get(0);
    }


    private String getListTime(Date createTime, Integer orderTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(createTime);
        switch (orderTime){
            case 1:
                //3个月
                cal.add(Calendar.MONTH,3);
                return formatter.format(cal.getTime());
            case 2:
                //半年
                cal.add(Calendar.MONTH,6);
                return formatter.format(cal.getTime());
            case 3:
                //一年
                cal.add(Calendar.MONTH,12);
                return formatter.format(cal.getTime());
            default:
                return null;
        }
    }

}
