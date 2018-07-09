package com.mobook.fastborrow.controller;

import com.mobook.fastborrow.constant.MAVUriConstant;
import com.mobook.fastborrow.constant.URLConstant;
import com.mobook.fastborrow.dataobject.HotKey;
import com.mobook.fastborrow.exception.FastBorrowException;
import com.mobook.fastborrow.form.HotKeyForm;
import com.mobook.fastborrow.service.HotKeyService;
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
 * @Date:Created in 14:59 2018\7\9 0009
 */
@Controller
@RequestMapping("/admin/hotkey")
public class FbHotKeyController {

    @Autowired
    private HotKeyService hotKeyService;

    @GetMapping("/list")
    public ModelAndView list(Map<String,Object> map){
        List<HotKey> hotKeyList = hotKeyService.findAll();
        map.put("hotKeyList",hotKeyList);
        return new ModelAndView(MAVUriConstant.HOTK_LIST,map);
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "id",required = false) Integer id,
                              Map<String,Object> map){
        if (id != null){
            HotKey hotKey = hotKeyService.findOne(id);
            map.put("hotKey",hotKey);
        }
        return new ModelAndView(MAVUriConstant.HOTK_INDEX,map);
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid HotKeyForm form,
                             BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return MAVUtils.setResultMOV(MAVUriConstant.ERROR,bindingResult.getFieldError().getDefaultMessage(),
                    URLConstant.BASE + URLConstant.HOTK_INDEX);
        }
        HotKey hotKey = new HotKey();
        try {
            if (form.getId() != null){
                hotKey = hotKeyService.findOne(form.getId());
            }
            BeanUtils.copyProperties(form,hotKey);
            hotKeyService.save(hotKey);
        } catch (FastBorrowException e){
            return MAVUtils.setResultMOV(MAVUriConstant.ERROR,e.getMessage(),
                    URLConstant.BASE + URLConstant.HOTK_INDEX);
        }
        return MAVUtils.setResultMOV(MAVUriConstant.SUCCESS,null,
                URLConstant.BASE+URLConstant.HOTK_LIST);
    }

}
