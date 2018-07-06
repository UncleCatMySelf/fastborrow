package com.mobook.fastborrow.controller;

import com.mobook.fastborrow.constant.LogMsgConstant;
import com.mobook.fastborrow.constant.MAVUriConstant;
import com.mobook.fastborrow.constant.URLConstant;
import com.mobook.fastborrow.dataobject.BookMessage;
import com.mobook.fastborrow.dataobject.BookWhere;
import com.mobook.fastborrow.exception.FastBorrowException;
import com.mobook.fastborrow.form.BookWhereForm;
import com.mobook.fastborrow.service.BookMessageService;
import com.mobook.fastborrow.service.BookWhereService;
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
 * @Date:Created in 15:42 2018\6\30 0030
 */
@Controller
@RequestMapping("/admin/bookwhere")
public class FbBookWhereController {

    @Autowired
    private BookWhereService bookWhereService;
    @Autowired
    private BookMessageService bookMessageService;

    @GetMapping("/list")
    public ModelAndView list(Map<String,Object> map){
        List<BookWhere> bookWhereList = bookWhereService.findAll();
        map.put("bookWhereList",bookWhereList);
        return new ModelAndView(MAVUriConstant.BOOK_WHERE_LIST,map);
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "id",required = false)Integer id,
                              Map<String,Object> map){
        if (id != null){
            BookWhere bookWhere = bookWhereService.findOne(id);
            map.put("bookWhere",bookWhere);
        }
        return new ModelAndView(MAVUriConstant.BOOK_WHERE_INDEX,map);
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid BookWhereForm form,
                             BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return MAVUtils.setResultMOV(MAVUriConstant.ERROR,bindingResult.getFieldError().getDefaultMessage(),
                    URLConstant.BASE+URLConstant.BOOKWHERE_INDEX);
        }
        BookWhere bookWhere = new BookWhere();
        try {
            if (form.getId() != null){
                bookWhere = bookWhereService.findOne(form.getId());
                List<BookMessage> bookMessageList = bookMessageService.findByWhereTag(bookWhere.getWhereTag());
                if (bookMessageList.size() > 0){
                    return MAVUtils.setResultMOV(MAVUriConstant.ERROR, LogMsgConstant.TYPE_UESING,
                            URLConstant.BASE+URLConstant.BOOKWHERE_LIST);
                }
            }
            BeanUtils.copyProperties(form,bookWhere);
            bookWhereService.save(bookWhere);
        }catch (FastBorrowException e){
            return MAVUtils.setResultMOV(MAVUriConstant.ERROR,e.getMessage(),
                    URLConstant.BASE+URLConstant.BOOKWHERE_INDEX);
        }
        return MAVUtils.setResultMOV(MAVUriConstant.SUCCESS,null,
                URLConstant.BASE+URLConstant.BOOKWHERE_LIST);
    }

}
