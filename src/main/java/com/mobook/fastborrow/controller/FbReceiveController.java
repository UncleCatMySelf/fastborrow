package com.mobook.fastborrow.controller;

import com.mobook.fastborrow.constant.MAVUriConstant;
import com.mobook.fastborrow.dataobject.Logistics;
import com.mobook.fastborrow.service.LogisticsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 用户收货地址后台模块
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 11:15 2018\7\21 0021
 */
@Controller
@RequestMapping("/admin/receive")
public class FbReceiveController {

    @Autowired
    private LogisticsService logisticsService;

    @GetMapping("/search")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "10") Integer size,
                             @RequestParam(value = "userId",required = false) String userId,
                             Map<String, Object> map){
        PageRequest request = new PageRequest(page - 1,size);
        Page<Logistics> logisticsPage = null;
        if (StringUtils.isEmpty(userId)){
            logisticsPage = logisticsService.findAll(request);
        }else{
            logisticsPage = logisticsService.findByUserId(Integer.parseInt(userId),request);
        }
        map.put("logisticsPage",logisticsPage);
        map.put("currentPage", page);
        map.put("size", size);
        map.put("userId", userId);
        return new ModelAndView(MAVUriConstant.RECEIVE_SEARCH,map);
    }

}
