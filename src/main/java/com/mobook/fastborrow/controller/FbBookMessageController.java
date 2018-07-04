package com.mobook.fastborrow.controller;

import com.mobook.fastborrow.constant.MAVUriConstant;
import com.mobook.fastborrow.constant.URLConstant;
import com.mobook.fastborrow.dataobject.BookMessage;
import com.mobook.fastborrow.dataobject.BookWhere;
import com.mobook.fastborrow.dataobject.Inventory;
import com.mobook.fastborrow.dataobject.Tag;
import com.mobook.fastborrow.enums.BookStatusEnum;
import com.mobook.fastborrow.exception.FastBorrowException;
import com.mobook.fastborrow.form.BookMessageForm;
import com.mobook.fastborrow.service.BookMessageService;
import com.mobook.fastborrow.service.BookWhereService;
import com.mobook.fastborrow.service.InventoryService;
import com.mobook.fastborrow.service.TagService;
import com.mobook.fastborrow.utils.KeyUtil;
import com.mobook.fastborrow.utils.MAVUtils;
import org.apache.commons.lang3.StringUtils;
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

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 15:08 2018\6\30 0030
 */
@Controller
@RequestMapping("/admin/bookmessage")
public class FbBookMessageController {

    @Autowired
    private BookMessageService bookMessageService;

    @Autowired
    private BookWhereService bookWhereService;

    @Autowired
    private TagService tagService;

    @Autowired
    private InventoryService inventoryService;

    //@GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "10") Integer size,
                             Map<String, Object> map){
        PageRequest request = new PageRequest(page - 1,size);
        Page<BookMessage> bookMessagePage = bookMessageService.findAll(request);
        List<BookWhere> bookWhereList = bookWhereService.findAll();
        //查询所有分类
        List<Tag> tagList = tagService.findAll();
        map.put("tagList",tagList);
        map.put("bookMessagePage",bookMessagePage);
        map.put("bookWhereList",bookWhereList);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView(MAVUriConstant.BOOK_LITS,map);
    }

    /**
     * 查询存在分页bug
     * @param page
     * @param size
     * @param mobookId
     * @param bookName
     * @param map
     * @return
     */
    @GetMapping("/search")
    public ModelAndView search(@RequestParam(value = "page",defaultValue = "1") Integer page,
                               @RequestParam(value = "size",defaultValue = "10") Integer size,
                               @RequestParam(value = "mobookId",defaultValue = "") String mobookId,
                               @RequestParam(value = "bookName",defaultValue = "") String bookName,Map<String, Object> map){
        PageRequest request = new PageRequest(page - 1,size);
        Page<BookMessage> bookMessagePage = null;
        if (!StringUtils.isEmpty(mobookId) && !StringUtils.isEmpty(bookName)){
            bookMessagePage = bookMessageService.findByMobookIdAndBookName(mobookId,bookName,request);
        }else if (StringUtils.isEmpty(mobookId) && !StringUtils.isEmpty(bookName)){
            bookMessagePage = bookMessageService.findByBookName(bookName,request);
        }else if (!StringUtils.isEmpty(mobookId) && StringUtils.isEmpty(bookName)){
            bookMessagePage = bookMessageService.findByMobookId(mobookId,request);
        }else {
            bookMessagePage = bookMessageService.findAll(request);
        }
        List<BookWhere> bookWhereList = bookWhereService.findAll();
        //查询所有分类
        List<Tag> tagList = tagService.findAll();
        map.put("tagList",tagList);
        map.put("bookMessagePage",bookMessagePage);
        map.put("bookWhereList",bookWhereList);
        map.put("currentPage", page);
        map.put("size", size);
        map.put("sMobookId",mobookId);
        map.put("sBookName",bookName);
        return new ModelAndView(MAVUriConstant.BOOK_LITS,map);
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "mobookId", required = false) String mobookId,
                              Map<String,Object> map){
        if (mobookId != null){
            BookMessage bookMessage = bookMessageService.findOne(mobookId);
            map.put("bookMessage",bookMessage);
        }
        //查询所有位置
        List<BookWhere> bookWhereList = bookWhereService.findAll();
        map.put("bookWhereList",bookWhereList);
        //查询所有分类
        List<Tag> tagList = tagService.findAll();
        map.put("tagList",tagList);
        return new ModelAndView(MAVUriConstant.BOOK_INDEX,map);
    }

    @Transactional
    @PostMapping("/save")
    public ModelAndView save(@Valid BookMessageForm form, BindingResult bindingResult,Map<String,Object> map){
        if (bindingResult.hasErrors()){
            return MAVUtils.setResultMOV(MAVUriConstant.ERROR,bindingResult.getFieldError().getDefaultMessage(),
                    URLConstant.BASE+URLConstant.BOOKMESSAGE_LIST);
        }
        BookMessage bookMessage = new BookMessage();
        Inventory inventory = new Inventory();
        try {
            // id空则新增
            if (!StringUtils.isEmpty(form.getMobookId())){
                bookMessage = bookMessageService.findOne(form.getMobookId());
            }else{
                //墨书id生成
                String keyId = KeyUtil.getMobookKey(form.getTagNum());
                form.setMobookId(keyId);
                inventory.setNum(1);
                inventory.setStatusNum(1);
                inventory.setBookName(form.getBookName());
                inventory.setIsbn(form.getIsbn());
            }
            BeanUtils.copyProperties(form,bookMessage);
            bookMessageService.save(bookMessage);
            inventoryService.save(inventory);
        } catch (FastBorrowException e){
            return MAVUtils.setResultMOV(MAVUriConstant.ERROR,e.getMessage(),
                    URLConstant.BASE+URLConstant.BOOKMESSAGE_INDEX);
        }
        return MAVUtils.setResultMOV(MAVUriConstant.SUCCESS,null,
                URLConstant.BASE+URLConstant.BOOKMESSAGE_LIST);
    }

    @RequestMapping("/on_sale")
    public ModelAndView onSale(@RequestParam("mobookId") String mobookId){
        try {
            bookMessageService.onSale(mobookId);
        }catch (FastBorrowException e){
            return MAVUtils.setResultMOV(MAVUriConstant.ERROR,e.getMessage(),
                    URLConstant.BASE+URLConstant.BOOKMESSAGE_LIST);
        }
        return MAVUtils.setResultMOV(MAVUriConstant.SUCCESS,null,
                URLConstant.BASE+URLConstant.BOOKMESSAGE_LIST);
    }

    @RequestMapping("/off_sale")
    public ModelAndView offSale(@RequestParam("mobookId") String mobookId){
        try {
            bookMessageService.offSale(mobookId);
        }catch (FastBorrowException e){
            return MAVUtils.setResultMOV(MAVUriConstant.ERROR,e.getMessage(),
                    URLConstant.BASE+URLConstant.BOOKMESSAGE_LIST);
        }
        return MAVUtils.setResultMOV(MAVUriConstant.SUCCESS,null,
                URLConstant.BASE+URLConstant.BOOKMESSAGE_LIST);
    }

    @GetMapping("/nblist")
    public ModelAndView nblist(Map<String, Object> map){
        List<BookMessage> bookMessageList = bookMessageService.findByStatus(BookStatusEnum.NEWBOOK.getCode());
        List<Tag> tagList = tagService.findAll();
        map.put("tagList",tagList);
        map.put("bookMessageList",bookMessageList);
        return new ModelAndView(MAVUriConstant.BOOK_NBLITS, map);
    }

    @GetMapping("/zblist")
    public ModelAndView zblist(Map<String, Object> map){
        List<BookMessage> bookMessageList = bookMessageService.findByStatus(BookStatusEnum.EDITOR.getCode());
        List<Tag> tagList = tagService.findAll();
        map.put("tagList",tagList);
        map.put("bookMessageList",bookMessageList);
        return new ModelAndView(MAVUriConstant.BOOK_ZBLITS, map);
    }

    @GetMapping("/nb_search")
    public ModelAndView nbSearch(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                 @RequestParam(value = "size",defaultValue = "10") Integer size,
                                 @RequestParam(value = "mobookId",defaultValue = "") String mobookId,
                                 @RequestParam(value = "bookName",defaultValue = "") String bookName,Map<String, Object> map){
        PageRequest request = new PageRequest(page - 1,size);
        Page<BookMessage> bookMessagePage = null;
        if (!StringUtils.isEmpty(mobookId) && !StringUtils.isEmpty(bookName)){
            bookMessagePage = bookMessageService.findByMobookIdIsLikeAndBookNameIsLikeAndStatus(mobookId,bookName,BookStatusEnum.UP.getCode(),request);
        }else if (StringUtils.isEmpty(mobookId) && !StringUtils.isEmpty(bookName)){
            bookMessagePage = bookMessageService.findByBookNameIsLikeAndStatus(bookName,BookStatusEnum.UP.getCode(),request);
        }else if (!StringUtils.isEmpty(mobookId) && StringUtils.isEmpty(bookName)){
            bookMessagePage = bookMessageService.findByMobookIdIsLikeAndStatus(mobookId,BookStatusEnum.UP.getCode(),request);
        }else{
            bookMessagePage = bookMessageService.findByStatus(BookStatusEnum.UP.getCode(),request);
        }
        List<BookWhere> bookWhereList = bookWhereService.findAll();
        //查询所有分类
        List<Tag> tagList = tagService.findAll();
        map.put("tagList",tagList);
        map.put("bookMessagePage",bookMessagePage);
        map.put("bookWhereList",bookWhereList);
        map.put("currentPage", page);
        map.put("size", size);
        map.put("sMobookId",mobookId);
        map.put("sBookName",bookName);
        return new ModelAndView(MAVUriConstant.BOOK_NBINDEX,map);
    }

    @GetMapping("/zb_search")
    public ModelAndView zbSearch(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                 @RequestParam(value = "size",defaultValue = "10") Integer size,
                                 @RequestParam(value = "mobookId",defaultValue = "") String mobookId,
                                 @RequestParam(value = "bookName",defaultValue = "") String bookName,Map<String, Object> map){
        PageRequest request = new PageRequest(page - 1,size);
        Page<BookMessage> bookMessagePage = null;
        if (!StringUtils.isEmpty(mobookId) && !StringUtils.isEmpty(bookName)){
            bookMessagePage = bookMessageService.findByMobookIdIsLikeAndBookNameIsLikeAndStatus(mobookId,bookName,BookStatusEnum.UP.getCode(),request);
        }else if (StringUtils.isEmpty(mobookId) && !StringUtils.isEmpty(bookName)){
            bookMessagePage = bookMessageService.findByBookNameIsLikeAndStatus(bookName,BookStatusEnum.UP.getCode(),request);
        }else if (!StringUtils.isEmpty(mobookId) && StringUtils.isEmpty(bookName)){
            bookMessagePage = bookMessageService.findByMobookIdIsLikeAndStatus(mobookId,BookStatusEnum.UP.getCode(),request);
        }else{
            bookMessagePage = bookMessageService.findByStatus(BookStatusEnum.UP.getCode(),request);
        }
        List<BookWhere> bookWhereList = bookWhereService.findAll();
        //查询所有分类
        List<Tag> tagList = tagService.findAll();
        map.put("tagList",tagList);
        map.put("bookMessagePage",bookMessagePage);
        map.put("bookWhereList",bookWhereList);
        map.put("currentPage", page);
        map.put("size", size);
        map.put("sMobookId",mobookId);
        map.put("sBookName",bookName);
        return new ModelAndView(MAVUriConstant.BOOK_ZBINDEX,map);
    }

    @GetMapping("/nb_add")
    public ModelAndView nbAdd(@RequestParam(value = "mobookId",defaultValue = "") String mobookId){
        BookMessage bookMessage = bookMessageService.findOne(mobookId);
        bookMessage.setStatus(BookStatusEnum.NEWBOOK.getCode());
        bookMessageService.save(bookMessage);
        return MAVUtils.setResultMOV(MAVUriConstant.SUCCESS,null,
                URLConstant.BASE+URLConstant.BOOKMESSAGE_NBLIST);
    }

    @GetMapping("/zb_add")
    public ModelAndView zbAdd(@RequestParam(value = "mobookId",defaultValue = "") String mobookId){
        BookMessage bookMessage = bookMessageService.findOne(mobookId);
        bookMessage.setStatus(BookStatusEnum.EDITOR.getCode());
        bookMessageService.save(bookMessage);
        return MAVUtils.setResultMOV(MAVUriConstant.SUCCESS,null,
                URLConstant.BASE+URLConstant.BOOKMESSAGE_ZBLIST);
    }

    @GetMapping("/no_newbook")
    public ModelAndView noNewbook(@RequestParam(value = "mobookId",defaultValue = "") String mobookId){
        BookMessage bookMessage = bookMessageService.findOne(mobookId);
        bookMessage.setStatus(BookStatusEnum.UP.getCode());
        bookMessageService.save(bookMessage);
        return MAVUtils.setResultMOV(MAVUriConstant.SUCCESS,null,
                URLConstant.BASE+URLConstant.BOOKMESSAGE_NBLIST);
    }

    @GetMapping("/no_zbbook")
    public ModelAndView noZbbook(@RequestParam(value = "mobookId",defaultValue = "") String mobookId){
        BookMessage bookMessage = bookMessageService.findOne(mobookId);
        bookMessage.setStatus(BookStatusEnum.UP.getCode());
        bookMessageService.save(bookMessage);
        return MAVUtils.setResultMOV(MAVUriConstant.SUCCESS,null,
                URLConstant.BASE+URLConstant.BOOKMESSAGE_ZBLIST);
    }
}
