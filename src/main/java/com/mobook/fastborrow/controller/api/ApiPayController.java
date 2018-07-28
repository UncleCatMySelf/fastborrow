package com.mobook.fastborrow.controller.api;

import com.lly835.bestpay.model.PayResponse;
import com.mobook.fastborrow.constant.AppPay;
import com.mobook.fastborrow.constant.MAVUriConstant;
import com.mobook.fastborrow.constant.RedisConstant;
import com.mobook.fastborrow.constant.WXLogMsgConstant;
import com.mobook.fastborrow.dataobject.User;
import com.mobook.fastborrow.dto.OrderDTO;
import com.mobook.fastborrow.enums.ResultEnum;
import com.mobook.fastborrow.exception.FastBorrowException;
import com.mobook.fastborrow.service.OrderMasterService;
import com.mobook.fastborrow.service.PayService;
import com.mobook.fastborrow.service.UserService;
import com.mobook.fastborrow.utils.ResultVOUtil;
import com.mobook.fastborrow.vo.ResultVO;
import com.mobook.fastborrow.vo.WxUserLoginVO;
import com.mobook.fastborrow.wechatpay.WxResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

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
    private UserService userService;

    @GetMapping("/create")
    public ResultVO create(@RequestHeader("token") String token,
                           @RequestParam("orderId") String orderId){
        //检查Token并获取token对应的用户id
        WxUserLoginVO wxUserLoginVO = new WxUserLoginVO();
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

    @GetMapping("/deposit")
    public ResultVO deposit(@RequestHeader("token") String token){
        //检查Token并获取token对应的用户id
        WxUserLoginVO wxUserLoginVO = new WxUserLoginVO();
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.WX_TONEKN_PREFIX,token));
        User user = userService.findOne(Integer.parseInt(tokenValue));
        OrderDTO orderDTO = orderMasterService.createDeposit(user.getOpenId());

        WxResponse wxResponse = payService.create(orderDTO);

        return ResultVOUtil.success(wxResponse);
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
}
