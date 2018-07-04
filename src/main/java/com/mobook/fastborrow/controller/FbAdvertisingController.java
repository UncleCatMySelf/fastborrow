package com.mobook.fastborrow.controller;

import com.mobook.fastborrow.constant.MAVUriConstant;
import com.mobook.fastborrow.constant.URLConstant;
import com.mobook.fastborrow.dataobject.AdverAddress;
import com.mobook.fastborrow.dataobject.Advertising;
import com.mobook.fastborrow.exception.FastBorrowException;
import com.mobook.fastborrow.form.AdvertisingForm;
import com.mobook.fastborrow.service.AdverAddressService;
import com.mobook.fastborrow.service.AdvertisingService;
import com.mobook.fastborrow.utils.MAVUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 11:57 2018\6\16 0016
 */
@Controller
@RequestMapping("/admin/advertising")
public class FbAdvertisingController {

    @Autowired
    private AdvertisingService advertisingService;

    @Autowired
    private AdverAddressService adverAddressService;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "10") Integer size,
                             Map<String, Object> map){
        PageRequest request = new PageRequest(page - 1,size);
        Page<Advertising> advertisingPage = advertisingService.findAll(request);
        List<AdverAddress> adverAddressList = adverAddressService.findAll();
        map.put("adverAddressList",adverAddressList);
        map.put("advertisingPage",advertisingPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView(MAVUriConstant.ADVERTIS_LIST, map);
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "adverId", required = false) Integer adverId,
                              Map<String,Object> map){
        if (adverId != null){
            Advertising advertising = advertisingService.findOne(adverId);
            map.put("advertising", advertising);
        }
        //查询所有位置
        List<AdverAddress> adverAddressList = adverAddressService.findAll();
        map.put("adverAddressList", adverAddressList);
        return new ModelAndView(MAVUriConstant.ADVERTIS_INDEX, map);
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid AdvertisingForm form,BindingResult bindingResult,
                             Map<String,Object> map){
        if (bindingResult.hasErrors()){
            return MAVUtils.setResultMOV(MAVUriConstant.ERROR,bindingResult.getFieldError().getDefaultMessage(),
                    URLConstant.BASE+URLConstant.ADV_INDEX);
        }
        Advertising advertising = new Advertising();
        try {
            // id空则新增
            if (form.getAdverId() != null){
                advertising = advertisingService.findOne(form.getAdverId());
            }
            BeanUtils.copyProperties(form,advertising);
            advertisingService.save(advertising);
        } catch (FastBorrowException e){
            return MAVUtils.setResultMOV(MAVUriConstant.ERROR,e.getMessage(),
                    URLConstant.BASE+URLConstant.ADV_INDEX);
        }
        return MAVUtils.setResultMOV(MAVUriConstant.SUCCESS,null,
                URLConstant.BASE+URLConstant.ADV_LIST);
    }

    /**
     * 广告上架
     * @param adverId
     * @return
     */
    @RequestMapping("/on_sale")
    public ModelAndView onSale(@RequestParam("adverId") Integer adverId){
        try {
            advertisingService.onSale(adverId);
        }catch (FastBorrowException e){
            return MAVUtils.setResultMOV(MAVUriConstant.ERROR,e.getMessage(),
                    URLConstant.BASE+URLConstant.ADV_LIST);
        }
        return MAVUtils.setResultMOV(MAVUriConstant.SUCCESS,null,
                URLConstant.BASE+URLConstant.ADV_LIST);
    }

    /**
     * 广告下架
     * @param adverId
     * @return
     */
    @RequestMapping("/off_sale")
    public ModelAndView offSale(@RequestParam("adverId") Integer adverId){
        try {
            advertisingService.offSale(adverId);
        }catch (FastBorrowException e){
            return MAVUtils.setResultMOV(MAVUriConstant.ERROR,e.getMessage(),
                    URLConstant.BASE+URLConstant.ADV_LIST);
        }
        return MAVUtils.setResultMOV(MAVUriConstant.SUCCESS,null,
                URLConstant.BASE+URLConstant.ADV_LIST);
    }

}
