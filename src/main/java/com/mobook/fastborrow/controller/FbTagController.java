package com.mobook.fastborrow.controller;

import com.mobook.fastborrow.constant.LogMsgConstant;
import com.mobook.fastborrow.constant.MAVUriConstant;
import com.mobook.fastborrow.constant.URLConstant;
import com.mobook.fastborrow.dataobject.AdverAddress;
import com.mobook.fastborrow.dataobject.BookMessage;
import com.mobook.fastborrow.dataobject.Tag;
import com.mobook.fastborrow.exception.FastBorrowException;
import com.mobook.fastborrow.form.AdverAddressForm;
import com.mobook.fastborrow.form.TagForm;
import com.mobook.fastborrow.service.BookMessageService;
import com.mobook.fastborrow.service.TagService;
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
 * @Date:Created in 16:45 2018\6\30 0030
 */
@Controller
@RequestMapping("/admin/tag")
public class FbTagController {

    @Autowired
    private TagService tagService;
    @Autowired
    private BookMessageService bookMessageService;

    @GetMapping("/list")
    public ModelAndView list(Map<String, Object> map){
        List<Tag> tagList = tagService.findAll();
        map.put("tagList",tagList);
        return new ModelAndView(MAVUriConstant.TAG_LIST,map);
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "tagId",required = false) Integer tagId,
                              Map<String,Object> map){
        if (tagId != null){
            Tag tag = tagService.findOne(tagId);
            map.put("tag",tag);
        }
        return new ModelAndView(MAVUriConstant.TAG_INDEX,map);
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid TagForm form,
                             BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return MAVUtils.setResultMOV(MAVUriConstant.ERROR,bindingResult.getFieldError().getDefaultMessage(),
                    URLConstant.BASE+URLConstant.TAG_INDEX);
        }
        Tag tag = new Tag();
        try {
            if (form.getTagId() != null){
                tag = tagService.findOne(form.getTagId());
                List<BookMessage> bookMessageList = bookMessageService.findByTagNum(tag.getTagNum());
                if (bookMessageList.size() > 0){
                    return MAVUtils.setResultMOV(MAVUriConstant.ERROR, LogMsgConstant.TYPE_UESING,
                            URLConstant.BASE+URLConstant.TAG_LIST);
                }
            }
            BeanUtils.copyProperties(form,tag);
            tagService.save(tag);
        } catch (FastBorrowException e){
            return MAVUtils.setResultMOV(MAVUriConstant.ERROR,e.getMessage(),
                    URLConstant.BASE+URLConstant.TAG_INDEX);
        }
        return MAVUtils.setResultMOV(MAVUriConstant.SUCCESS,null,
                URLConstant.BASE+URLConstant.TAG_LIST);
    }

}
