package com.mobook.fastborrow.controller.api;

import com.mobook.fastborrow.constant.RedisConstant;
import com.mobook.fastborrow.dataobject.Logistics;
import com.mobook.fastborrow.dataobject.OrderMaster;
import com.mobook.fastborrow.dataobject.User;
import com.mobook.fastborrow.enums.LogisticsStatusEnum;
import com.mobook.fastborrow.service.LogisticsService;
import com.mobook.fastborrow.service.OrderMasterService;
import com.mobook.fastborrow.service.UserService;
import com.mobook.fastborrow.utils.ResultVOUtil;
import com.mobook.fastborrow.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

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
    private StringRedisTemplate redisTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private LogisticsService logisticsService;

    @PostMapping("/buy")
    public ResultVO buy(@RequestHeader("token") String token, @RequestParam("isbns") String[] isbns,@RequestParam("renew") String renew,
                        @RequestParam("note") String note){
        //检查Token并获取token对应的用户id
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.WX_TONEKN_PREFIX,token));
        User user = userService.findOne(Integer.parseInt(tokenValue));
        //1、创建订单
        //2、下单
        OrderMaster orderMaster = orderMasterService.createBuy(user,isbns,renew,note);
        user.setIntegral(user.getIntegral()+orderMaster.getOrderAmount().intValue());
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

}
