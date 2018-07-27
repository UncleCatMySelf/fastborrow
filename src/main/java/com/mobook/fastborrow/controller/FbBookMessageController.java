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
import com.mobook.fastborrow.service.elastic.ISearchService;
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
    private ISearchService searchService;

    @Autowired
    private TagService tagService;

    /**
     * 查询存在分页bug
     * @param page
     * @param size
     * @param sIsbn
     * @param bookName
     * @param map
     * @return
     */
    @GetMapping("/search")
    public ModelAndView search(@RequestParam(value = "page",defaultValue = "1") Integer page,
                               @RequestParam(value = "size",defaultValue = "10") Integer size,
                               @RequestParam(value = "sIsbn",defaultValue = "") String sIsbn,
                               @RequestParam(value = "bookName",defaultValue = "") String bookName,Map<String, Object> map){
        PageRequest request = new PageRequest(page - 1,size);
        Page<BookMessage> bookMessagePage = null;
        if (!StringUtils.isEmpty(sIsbn) && !StringUtils.isEmpty(bookName)){
            bookMessagePage = bookMessageService.findByIsbnAndBookName(sIsbn,bookName,request);
        }else if (StringUtils.isEmpty(sIsbn) && !StringUtils.isEmpty(bookName)){
            bookMessagePage = bookMessageService.findByBookName(bookName,request);
        }else if (!StringUtils.isEmpty(sIsbn) && StringUtils.isEmpty(bookName)){
            bookMessagePage = bookMessageService.findByIsbn(sIsbn,request);
        }else {
            bookMessagePage = bookMessageService.findAll(request);
        }
        //查询所有分类
        List<Tag> tagList = tagService.findAll();
        map.put("tagList",tagList);
        map.put("bookMessagePage",bookMessagePage);
        map.put("currentPage", page);
        map.put("size", size);
        map.put("sIsbn",sIsbn);
        map.put("sBookName",bookName);
        return new ModelAndView(MAVUriConstant.BOOK_LITS,map);
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "isbn", required = false) String isbn,
                              Map<String,Object> map){
        if (isbn != null){
            BookMessage bookMessage = bookMessageService.findOne(isbn);
            map.put("bookMessage",bookMessage);
        }
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
        try {
            BeanUtils.copyProperties(form,bookMessage);
            bookMessage.setInfo(bookMessage.getInfo().trim());
            bookMessage.setSummary(bookMessage.getSummary().trim());
            bookMessage.setCatalog(bookMessage.getCatalog().trim());
            bookMessageService.save(bookMessage);
            searchService.index(bookMessage.getIsbn());
        } catch (FastBorrowException e){
            return MAVUtils.setResultMOV(MAVUriConstant.ERROR,e.getMessage(),
                    URLConstant.BASE+URLConstant.BOOKMESSAGE_INDEX);
        }
        return MAVUtils.setResultMOV(MAVUriConstant.SUCCESS,null,
                URLConstant.BASE+URLConstant.BOOKMESSAGE_LIST);
    }

    @RequestMapping("/on_sale")
    public ModelAndView onSale(@RequestParam("isbn") String isbn){
        try {
            bookMessageService.onSale(isbn);
            searchService.index(isbn);
        }catch (FastBorrowException e){
            return MAVUtils.setResultMOV(MAVUriConstant.ERROR,e.getMessage(),
                    URLConstant.BASE+URLConstant.BOOKMESSAGE_LIST);
        }
        return MAVUtils.setResultMOV(MAVUriConstant.SUCCESS,null,
                URLConstant.BASE+URLConstant.BOOKMESSAGE_LIST);
    }

    @RequestMapping("/off_sale")
    public ModelAndView offSale(@RequestParam("isbn") String isbn){
        try {
            bookMessageService.offSale(isbn);
            searchService.remove(isbn);
        }catch (FastBorrowException e){
            return MAVUtils.setResultMOV(MAVUriConstant.ERROR,e.getMessage(),
                    URLConstant.BASE+URLConstant.BOOKMESSAGE_LIST);
        }
        return MAVUtils.setResultMOV(MAVUriConstant.SUCCESS,null,
                URLConstant.BASE+URLConstant.BOOKMESSAGE_LIST);
    }

    private Map<String,Object> XXList(Integer status,Map<String,Object> map){
        List<BookMessage> bookMessageList = bookMessageService.findByStatus(status);
        List<Tag> tagList = tagService.findAll();
        map.put("tagList",tagList);
        map.put("bookMessageList",bookMessageList);
        return map;
    }

    @GetMapping("/nblist")
    public ModelAndView nblist(Map<String, Object> map){
        return new ModelAndView(MAVUriConstant.BOOK_NBLITS, XXList(BookStatusEnum.NEWBOOK.getCode(),map));
    }

    @GetMapping("/hots_list")
    public ModelAndView hotsList(Map<String, Object> map){
        return new ModelAndView(MAVUriConstant.HOTS_LIST, XXList(BookStatusEnum.HOTSEARCH.getCode(),map));
    }

    @GetMapping("/zblist")
    public ModelAndView zblist(Map<String, Object> map){
        return new ModelAndView(MAVUriConstant.BOOK_ZBLITS, XXList(BookStatusEnum.EDITOR.getCode(),map));
    }

    @GetMapping("/wlist")
    public ModelAndView wlist(Map<String, Object> map){
        return new ModelAndView(MAVUriConstant.BOOK_WLITS, XXList(BookStatusEnum.WINNING.getCode(),map));
    }

    private Map<String,Object> XXSearch(Integer page,Integer size,String isbn,String bookName,Map<String, Object> map){
        PageRequest request = new PageRequest(page - 1,size);
        Page<BookMessage> bookMessagePage = null;
        if (!StringUtils.isEmpty(isbn) && !StringUtils.isEmpty(bookName)){
            bookMessagePage = bookMessageService.findByIsbnIsLikeAndBookNameIsLikeAndStatus(isbn,bookName,BookStatusEnum.UP.getCode(),request);
        }else if (StringUtils.isEmpty(isbn) && !StringUtils.isEmpty(bookName)){
            bookMessagePage = bookMessageService.findByBookNameIsLikeAndStatus(bookName,BookStatusEnum.UP.getCode(),request);
        }else if (!StringUtils.isEmpty(isbn) && StringUtils.isEmpty(bookName)){
            bookMessagePage = bookMessageService.findByIsbnIsLikeAndStatus(isbn,BookStatusEnum.UP.getCode(),request);
        }else{
            bookMessagePage = bookMessageService.findByStatus(BookStatusEnum.UP.getCode(),request);
        }
        //查询所有分类
        List<Tag> tagList = tagService.findAll();
        map.put("tagList",tagList);
        map.put("bookMessagePage",bookMessagePage);
        map.put("currentPage", page);
        map.put("size", size);
        map.put("sIsbn",isbn);
        map.put("sBookName",bookName);
        return map;
    }

    @GetMapping("/hots_search")
    public ModelAndView hotSsearch(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                 @RequestParam(value = "size",defaultValue = "10") Integer size,
                                 @RequestParam(value = "isbn",defaultValue = "") String isbn,
                                 @RequestParam(value = "bookName",defaultValue = "") String bookName,Map<String, Object> map){
        return new ModelAndView(MAVUriConstant.HOTS_INDEX, XXSearch(page, size, isbn, bookName, map));
    }


    @GetMapping("/nb_search")
    public ModelAndView nbSearch(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                 @RequestParam(value = "size",defaultValue = "10") Integer size,
                                 @RequestParam(value = "isbn",defaultValue = "") String isbn,
                                 @RequestParam(value = "bookName",defaultValue = "") String bookName,Map<String, Object> map){
        return new ModelAndView(MAVUriConstant.BOOK_NBINDEX, XXSearch(page, size, isbn, bookName, map));
    }

    @GetMapping("/zb_search")
    public ModelAndView zbSearch(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                 @RequestParam(value = "size",defaultValue = "10") Integer size,
                                 @RequestParam(value = "isbn",defaultValue = "") String isbn,
                                 @RequestParam(value = "bookName",defaultValue = "") String bookName,Map<String, Object> map){
        return new ModelAndView(MAVUriConstant.BOOK_ZBINDEX, XXSearch(page, size, isbn, bookName, map));
    }

    @GetMapping("/w_search")
    public ModelAndView wSearch(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                 @RequestParam(value = "size",defaultValue = "10") Integer size,
                                 @RequestParam(value = "isbn",defaultValue = "") String isbn,
                                 @RequestParam(value = "bookName",defaultValue = "") String bookName,Map<String, Object> map){
        return new ModelAndView(MAVUriConstant.BOOK_WINDEX, XXSearch(page, size, isbn, bookName, map));
    }

    @GetMapping("/nb_add")
    public ModelAndView nbAdd(@RequestParam(value = "isbn",defaultValue = "") String isbn){
        BookMessage bookMessage = bookMessageService.findOne(isbn);
        bookMessage.setStatus(BookStatusEnum.NEWBOOK.getCode());
        bookMessageService.save(bookMessage);
        return MAVUtils.setResultMOV(MAVUriConstant.SUCCESS,null,
                URLConstant.BASE+URLConstant.BOOKMESSAGE_NBLIST);
    }

    @GetMapping("/hots_add")
    public ModelAndView hotsAdd(@RequestParam(value = "isbn",defaultValue = "") String isbn){
        List<BookMessage> bookMessageList = bookMessageService.findByStatus(BookStatusEnum.HOTSEARCH.getCode());
        if (bookMessageList.size() >= 4){
            return MAVUtils.setResultMOV(MAVUriConstant.ERROR,null,
                    URLConstant.BASE+URLConstant.HOTS_SEARCH_LIST);
        }else {
            BookMessage bookMessage = bookMessageService.findOne(isbn);
            bookMessage.setStatus(BookStatusEnum.HOTSEARCH.getCode());
            bookMessageService.save(bookMessage);
            return MAVUtils.setResultMOV(MAVUriConstant.SUCCESS,null,
                    URLConstant.BASE+URLConstant.HOTS_SEARCH_LIST);
        }
    }

    @GetMapping("/zb_add")
    public ModelAndView zbAdd(@RequestParam(value = "isbn",defaultValue = "") String isbn){
        BookMessage bookMessage = bookMessageService.findOne(isbn);
        bookMessage.setStatus(BookStatusEnum.EDITOR.getCode());
        bookMessageService.save(bookMessage);
        return MAVUtils.setResultMOV(MAVUriConstant.SUCCESS,null,
                URLConstant.BASE+URLConstant.BOOKMESSAGE_ZBLIST);
    }

    @GetMapping("/w_add")
    public ModelAndView wAdd(@RequestParam(value = "isbn",defaultValue = "") String isbn){
        BookMessage bookMessage = bookMessageService.findOne(isbn);
        bookMessage.setStatus(BookStatusEnum.WINNING.getCode());
        bookMessageService.save(bookMessage);
        return MAVUtils.setResultMOV(MAVUriConstant.SUCCESS,null,
                URLConstant.BASE+URLConstant.BOOKMESSAGE_WLIST);
    }

    private void updateStatus(String isbn){
        BookMessage bookMessage = bookMessageService.findOne(isbn);
        bookMessage.setStatus(BookStatusEnum.UP.getCode());
        bookMessageService.save(bookMessage);
    }


    @GetMapping("/no_newbook")
    public ModelAndView noNewbook(@RequestParam(value = "isbn",defaultValue = "") String isbn){
        updateStatus(isbn);
        return MAVUtils.setResultMOV(MAVUriConstant.SUCCESS,null,
                URLConstant.BASE+URLConstant.BOOKMESSAGE_NBLIST);
    }

    @GetMapping("/no_hotssearch")
    public ModelAndView noHotsSearch(@RequestParam(value = "isbn",defaultValue = "") String isbn){
        updateStatus(isbn);
        return MAVUtils.setResultMOV(MAVUriConstant.SUCCESS,null,
                URLConstant.BASE+URLConstant.HOTS_SEARCH_LIST);
    }

    @GetMapping("/no_zbbook")
    public ModelAndView noZbbook(@RequestParam(value = "isbn",defaultValue = "") String isbn){
        updateStatus(isbn);
        return MAVUtils.setResultMOV(MAVUriConstant.SUCCESS,null,
                URLConstant.BASE+URLConstant.BOOKMESSAGE_ZBLIST);
    }

    @GetMapping("/no_wbook")
    public ModelAndView noWbook(@RequestParam(value = "isbn",defaultValue = "") String isbn){
        updateStatus(isbn);
        return MAVUtils.setResultMOV(MAVUriConstant.SUCCESS,null,
                URLConstant.BASE+URLConstant.BOOKMESSAGE_WLIST);
    }
}
