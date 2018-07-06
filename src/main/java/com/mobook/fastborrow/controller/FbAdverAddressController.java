package com.mobook.fastborrow.controller;

import com.mobook.fastborrow.constant.LogMsgConstant;
import com.mobook.fastborrow.constant.MAVUriConstant;
import com.mobook.fastborrow.constant.MapMsgConstant;
import com.mobook.fastborrow.constant.URLConstant;
import com.mobook.fastborrow.dataobject.AdverAddress;
import com.mobook.fastborrow.dataobject.Advertising;
import com.mobook.fastborrow.exception.FastBorrowException;
import com.mobook.fastborrow.form.AdverAddressForm;
import com.mobook.fastborrow.service.AdverAddressService;
import com.mobook.fastborrow.service.AdvertisingService;
import com.mobook.fastborrow.utils.MAVUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @Date:Created in 21:04 2018\6\15 0015
 */
@Controller
@RequestMapping("/admin/adveraddress")
public class FbAdverAddressController {

    @Autowired
    private AdverAddressService adverAddressService;
    @Autowired
    private AdvertisingService advertisingService;

    @GetMapping("/list")
    public ModelAndView list(Map<String, Object> map){
        List<AdverAddress> adverAddresseList = adverAddressService.findAll();
        map.put("adverAddresseList",adverAddresseList);
        return new ModelAndView(MAVUriConstant.ADVADD_LIST, map);
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "id",required = false) Integer id,
                              Map<String,Object> map){
        if (id != null){
            AdverAddress adverAddress = adverAddressService.findOne(id);
            map.put("adverAddress",adverAddress);
        }
        return new ModelAndView(MAVUriConstant.ADVADD_INDEX, map);
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid AdverAddressForm form,
                             BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return MAVUtils.setResultMOV(MAVUriConstant.ERROR,bindingResult.getFieldError().getDefaultMessage(),
                    URLConstant.BASE+URLConstant.ADVADD_INDEX);
        }
        AdverAddress adverAddress = new AdverAddress();
        try {
            if (form.getId() != null){
                adverAddress = adverAddressService.findOne(form.getId());
                List<Advertising> advertisingList = advertisingService.findByAdverAddress(adverAddress.getAdvAdNum());
                if (advertisingList.size() > 0){
                    return MAVUtils.setResultMOV(MAVUriConstant.ERROR, LogMsgConstant.TYPE_UESING,
                            URLConstant.BASE+URLConstant.ADVADD_LIST);
                }
            }
            BeanUtils.copyProperties(form,adverAddress);
            adverAddressService.save(adverAddress);
        } catch (FastBorrowException e){
            return MAVUtils.setResultMOV(MAVUriConstant.ERROR,e.getMessage(),
                    URLConstant.BASE+URLConstant.ADVADD_INDEX);
        }
        return MAVUtils.setResultMOV(MAVUriConstant.SUCCESS,null,
                URLConstant.BASE+URLConstant.ADVADD_LIST);
    }

}
