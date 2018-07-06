package com.mobook.fastborrow.controller.api;

import com.mobook.fastborrow.converter.Advertising2AdvertisingVOConverter;
import com.mobook.fastborrow.dataobject.Advertising;
import com.mobook.fastborrow.enums.AdverStatusEnum;
import com.mobook.fastborrow.service.AdvertisingService;
import com.mobook.fastborrow.utils.ResultVOUtil;
import com.mobook.fastborrow.vo.AdvertisingVO;
import com.mobook.fastborrow.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 小程序广告业务
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 11:12 2018\7\4 0004
 */
@RestController
@RequestMapping("/api/v1/advertising")
@Slf4j
public class ApiAdvertisingController {

    @Autowired
    private AdvertisingService advertisingService;

    @GetMapping("/list")
    public ResultVO<List<AdvertisingVO>> list(@RequestParam("addressNum") Integer addressNum){
        List<Advertising> advertisingList = advertisingService.findByAdverAddressAndStatus(addressNum, AdverStatusEnum.UP.getCode());
        List<AdvertisingVO> advertisingVOList = Advertising2AdvertisingVOConverter.convert(advertisingList);
        return ResultVOUtil.success(advertisingVOList);
    }

}
