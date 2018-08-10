package com.mobook.fastborrow.controller.api;

import com.mobook.fastborrow.constant.MAVUriConstant;
import com.mobook.fastborrow.constant.RedisConstant;
import com.mobook.fastborrow.dataobject.Inventory;
import com.mobook.fastborrow.dataobject.OrderDetail;
import com.mobook.fastborrow.dataobject.OrderMaster;
import com.mobook.fastborrow.dataobject.User;
import com.mobook.fastborrow.dto.OrderDTO;
import com.mobook.fastborrow.enums.InventoryStatusEnum;
import com.mobook.fastborrow.enums.OrderStatusEnum;
import com.mobook.fastborrow.enums.PayStatusEnum;
import com.mobook.fastborrow.enums.ResultEnum;
import com.mobook.fastborrow.exception.FastBorrowException;
import com.mobook.fastborrow.service.*;
import com.mobook.fastborrow.utils.ResultVOUtil;
import com.mobook.fastborrow.vo.ResultVO;
import com.mobook.fastborrow.wechatpay.WxRefundResponse;
import com.mobook.fastborrow.wechatpay.WxResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.List;


/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 11:34 2018\7\27 0027
 */
@RestController
@RequestMapping("/api/v1/pay")
@Slf4j
public class ApiPayController {

    //押金与支付
    @Autowired
    private OrderMasterService orderMasterService;

    @Autowired
    private PayService payService;

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderDetailService orderDetailService;

    /**
     * 下订单支付
     * @param token
     * @param orderId
     * @return
     */
    @PostMapping("/create")
    public ResultVO create(@RequestHeader("token") String token,
                           @RequestParam("orderId") String orderId){
        //检查Token并获取token对应的用户id
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.WX_TONEKN_PREFIX,token));
        User user = userService.findOne(Integer.parseInt(tokenValue));
        //1、查询订单
        OrderDTO orderDTO = orderMasterService.findOne(orderId);
        if (orderDTO == null){
            throw new FastBorrowException(ResultEnum.ORDER_NOT_EXIST);
        }
        //2、发起支付
        WxResponse wxResponse = payService.create(orderDTO);
        return ResultVOUtil.success(wxResponse);
    }

    /**
     * 押金支付
     * @param token
     * @return
     */
    @GetMapping("/deposit")
    public ResultVO deposit(@RequestHeader("token") String token){
        //检查Token并获取token对应的用户id
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.WX_TONEKN_PREFIX,token));
        User user = userService.findOne(Integer.parseInt(tokenValue));
        OrderDTO orderDTO = orderMasterService.createDeposit(user.getOpenId());

        WxResponse wxResponse = payService.create(orderDTO);

        return ResultVOUtil.success(wxResponse);
    }

    @PostMapping("/refund")
    public ResultVO refund(@RequestHeader("token") String token){
        //检查Token并获取token对应的用户id
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.WX_TONEKN_PREFIX,token));
        User user = userService.findOne(Integer.parseInt(tokenValue));
        OrderDTO orderDTO = orderMasterService.findByBuyerOpenidToDeposit(user.getOpenId());
        WxRefundResponse wxRefundResponse = payService.refund(orderDTO);
        return ResultVOUtil.success(wxRefundResponse);
    }

    /**
     * 微信异步通知
     */
    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyData){
        payService.notify(notifyData);
        //返回给微信处理结果
        return new ModelAndView(MAVUriConstant.PAY_SUCCESS);
    }

    @PostMapping("/pay_success")
    public ResultVO paySuccess(@RequestHeader("token") String token){
        setDeposit(token,new BigDecimal(99.00));
        return ResultVOUtil.success();
    }

    private void setDeposit(@RequestHeader("token") String token,BigDecimal deposit) {
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.WX_TONEKN_PREFIX,token));
        User user = userService.findOne(Integer.parseInt(tokenValue));
        user.setUserDeposit(deposit);
        userService.save(user);
    }

    @PostMapping("/rund_success")
    public ResultVO rundSuccess(@RequestHeader("token") String token){
        setDeposit(token,new BigDecimal(0.0));
        return ResultVOUtil.success();
    }

    @PostMapping("/create_success")
    public ResultVO createSuccess(@RequestHeader("token") String token,
                                  @RequestParam("orderId") String orderId){
        //检查Token并获取token对应的用户id
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.WX_TONEKN_PREFIX,token));
        User user = userService.findOne(Integer.parseInt(tokenValue));
        OrderMaster orderMaster = orderMasterService.getOne(orderId);
        orderMaster.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        orderMasterService.save(orderMaster);
        return ResultVOUtil.success();
    }
}
