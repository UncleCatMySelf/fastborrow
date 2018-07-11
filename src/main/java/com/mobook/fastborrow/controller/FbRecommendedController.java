package com.mobook.fastborrow.controller;

import com.mobook.fastborrow.constant.MAVUriConstant;
import com.mobook.fastborrow.constant.URLConstant;
import com.mobook.fastborrow.dataobject.BookMessage;
import com.mobook.fastborrow.dataobject.Recommended;
import com.mobook.fastborrow.enums.BookStatusEnum;
import com.mobook.fastborrow.enums.RecommendedStatusEnum;
import com.mobook.fastborrow.exception.FastBorrowException;
import com.mobook.fastborrow.form.RecommendedForm;
import com.mobook.fastborrow.service.BookMessageService;
import com.mobook.fastborrow.service.RecommendedService;
import com.mobook.fastborrow.utils.MAVUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 15:37 2018\7\10 0010
 */
@Controller
@RequestMapping("/admin/recommended")
public class FbRecommendedController {

    @Autowired
    private RecommendedService recommendedService;
    @Autowired
    private BookMessageService bookMessageService;

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "page",defaultValue = "1") Integer page,
                              @RequestParam(value = "size",defaultValue = "10") Integer size,
                              @RequestParam(value = "mobookId",defaultValue = "") String mobookId,
                              @RequestParam(value = "bookName",defaultValue = "") String bookName,
                              @RequestParam(value = "id",required = false)Integer id,
                              @RequestParam(value = "title",defaultValue = "")String title,
                              @RequestParam(value = "info",defaultValue = "")String info,
                              @RequestParam(value = "author",defaultValue = "")String author,
                              @RequestParam(value = "time",defaultValue = "")String time,
                              @RequestParam(value = "images",defaultValue = "")String images,
                              @RequestParam(value = "cssstatus",defaultValue = "1")Integer cssstatus,
                              Map<String,Object> map){
        PageRequest request = new PageRequest(page - 1,size);
        Page<BookMessage> bookMessagePage = null;
        if (id != null){
            Recommended recommended = recommendedService.findOne(id);
            map.put("recommended",recommended);
        }
        if (!StringUtils.isEmpty(mobookId) && !StringUtils.isEmpty(bookName)){
            bookMessagePage = bookMessageService.findByMobookIdIsLikeAndBookNameIsLikeAndStatusIsNot(mobookId,bookName, BookStatusEnum.DOWN.getCode(),request);
        }else if (StringUtils.isEmpty(mobookId) && !StringUtils.isEmpty(bookName)){
            bookMessagePage = bookMessageService.findByBookNameIsLikeAndStatusIsNot(bookName,BookStatusEnum.DOWN.getCode(),request);
        }else if (!StringUtils.isEmpty(mobookId) && StringUtils.isEmpty(bookName)){
            bookMessagePage = bookMessageService.findByMobookIdIsLikeAndStatusIsNot(mobookId,BookStatusEnum.DOWN.getCode(),request);
        }else{
            bookMessagePage = bookMessageService.findByStatusIsNot(BookStatusEnum.DOWN.getCode(),request);
        }
        map.put("bookMessagePage",bookMessagePage);
        map.put("currentPage", page);
        map.put("size", size);
        map.put("title",title);
        map.put("info",info);
        map.put("author",author);
        map.put("time",time);
        map.put("images",images);
        map.put("cssstatus",cssstatus);
        map.put("sMobookId",mobookId);
        map.put("sBookName",bookName);
        return new ModelAndView(MAVUriConstant.REC_INDEX,map);
    }

    @Transactional
    @GetMapping("/save")
    public ModelAndView save(@Valid RecommendedForm form, BindingResult bindingResult, Map<String,Object> map){
        if (bindingResult.hasErrors()){
            return MAVUtils.setResultMOV(MAVUriConstant.ERROR,bindingResult.getFieldError().getDefaultMessage(),
                    URLConstant.BASE+URLConstant.REC_LIST);
        }
        Recommended recommended = new Recommended();
        try {
            if (form.getId() != null){
                recommended = recommendedService.findOne(form.getId());
            }
            BeanUtils.copyProperties(form,recommended);
            recommended.setCss(form.getCssstatus());
            Recommended saveItem = recommendedService.save(recommended);
            for (String bookMessid : form.getBookMessIdList()){
                System.err.println(bookMessid);
                BookMessage bookMessage = bookMessageService.findOne(bookMessid);
                bookMessage.setRecNum(saveItem.getId());
                bookMessageService.save(bookMessage);
            }
        }catch (FastBorrowException e){
            return MAVUtils.setResultMOV(MAVUriConstant.ERROR,e.getMessage(),
                    URLConstant.BASE+URLConstant.REC_INDEX);
        }
        return MAVUtils.setResultMOV(MAVUriConstant.SUCCESS,null,
                URLConstant.BASE+URLConstant.REC_LIST);
    }

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "10") Integer size,
                             @RequestParam(value = "title",defaultValue = "") String title,
                             Map<String, Object> map){
        PageRequest request = new PageRequest(page - 1,size);
        Page<Recommended> recommendedPage = null;
        if (!StringUtils.isEmpty(title)){
            recommendedPage = recommendedService.findAllByTitleIsLike(title,request);
        }else{
            recommendedPage = recommendedService.findAll(request);
        }
        map.put("recommendedPage",recommendedPage);
        map.put("title",title);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView(MAVUriConstant.REC_LIST,map);
    }

    @RequestMapping("/off_sale")
    public ModelAndView offSale(@RequestParam("id") Integer id){
        Recommended recommended = null;
        try {
            recommended = recommendedService.findOne(id);
            List<BookMessage> bookMessageList = bookMessageService.findByRecNum(id);
            for (BookMessage item : bookMessageList){
                item.setRecNum(0);
                bookMessageService.save(item);
            }
            recommended.setStatus(RecommendedStatusEnum.DOWN.getCode());
            recommendedService.save(recommended);
        }catch (FastBorrowException e){
            return MAVUtils.setResultMOV(MAVUriConstant.ERROR,e.getMessage(),
                    URLConstant.BASE+URLConstant.REC_LIST);
        }
        return MAVUtils.setResultMOV(MAVUriConstant.SUCCESS,null,
                URLConstant.BASE+URLConstant.REC_LIST);
    }


}
