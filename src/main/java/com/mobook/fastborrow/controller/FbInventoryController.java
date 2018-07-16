package com.mobook.fastborrow.controller;

import com.mobook.fastborrow.constant.MAVUriConstant;
import com.mobook.fastborrow.dataobject.BookMessage;
import com.mobook.fastborrow.dataobject.BookWhere;
import com.mobook.fastborrow.dataobject.Inventory;
import com.mobook.fastborrow.dataobject.Tag;
import com.mobook.fastborrow.service.BookMessageService;
import com.mobook.fastborrow.service.BookWhereService;
import com.mobook.fastborrow.service.InventoryService;
import com.mobook.fastborrow.service.TagService;
import com.mobook.fastborrow.utils.KeyUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 16:06 2018\7\2 0002
 */
@Controller
@RequestMapping("/admin/inventory")
public class FbInventoryController {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private BookWhereService bookWhereService;

    @Autowired
    private BookMessageService bookMessageService;

    @GetMapping("/search")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "10") Integer size,
                             @RequestParam(value = "isbn",defaultValue = "") String isbn,
                             @RequestParam(value = "bookName",defaultValue = "") String bookName,
                             Map<String, Object> map){
        PageRequest request = new PageRequest(page - 1,size);
        Page<Inventory> inventoriePage = null;
        if (!StringUtils.isEmpty(isbn) && !StringUtils.isEmpty(bookName)){
            inventoriePage = inventoryService.findByIsbnISLikeAndBookNameISLike(isbn,bookName,request);
        }else if (StringUtils.isEmpty(isbn) && !StringUtils.isEmpty(bookName)){
            inventoriePage = inventoryService.findByBookNameISLike(bookName,request);
        }else if (!StringUtils.isEmpty(isbn) && StringUtils.isEmpty(bookName)){
            inventoriePage = inventoryService.findByIsbnISLike(isbn,request);
        }else{
            inventoriePage = inventoryService.findAll(request);
        }
        map.put("inventoriePage",inventoriePage);
        map.put("currentPage", page);
        map.put("size", size);
        map.put("sIsbn",isbn);
        map.put("sBookName",bookName);
        return new ModelAndView(MAVUriConstant.INVEN_LIST,map);
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "isbn",defaultValue = "") String isbn,
                              Map<String,Object> map){
        BookMessage bookMessage = new BookMessage();
        //isbn唯一查询
        if (!StringUtils.isEmpty(isbn)){
            Inventory inventory = inventoryService.findByIsbn(isbn);
            if (inventory != null){
                List<BookMessage> bookMessageList = bookMessageService.findByIsbn(inventory.getIsbn());
                BookMessage bookMessageItem = bookMessageList.get(0);
                BeanUtils.copyProperties(bookMessageItem,bookMessage);
            }
        }
        List<BookWhere> bookWhereList = bookWhereService.findAll();
        List<Tag> tagList = tagService.findAll();
        map.put("tagList",tagList);
        map.put("bookWhereList",bookWhereList);
        map.put("sIsbn",isbn);
        map.put("bookMessage",bookMessage);
        map.put("newMobookId",null);
        return new ModelAndView(MAVUriConstant.INVEN_INDEX,map);
    }

    @Transactional
    @PostMapping("/add")
    public ModelAndView add(@RequestParam("whereTag") String whereTag,
                            @RequestParam("isbn") String isbn,Map<String,Object> map){
        BookMessage bookMessage = new BookMessage();
        String keyId = null;
        if (!StringUtils.isEmpty(isbn)){
            Inventory inventory = inventoryService.findByIsbn(isbn);
            if (inventory != null){
                List<BookMessage> bookMessageList = bookMessageService.findByIsbn(inventory.getIsbn());
                BookMessage bookMessageItem = bookMessageList.get(0);
                BeanUtils.copyProperties(bookMessageItem,bookMessage);
                bookMessage.setWhereTag(whereTag);
                keyId = KeyUtil.getMobookKey(bookMessageItem.getTagNum());
                bookMessage.setMobookId(keyId);
                bookMessageService.save(bookMessage);
                inventory.setNum(inventory.getNum()+1);
                inventory.setStatusNum(inventory.getStatusNum()+1);
                inventoryService.save(inventory);
            }
        }
        List<BookWhere> bookWhereList = bookWhereService.findAll();
        List<Tag> tagList = tagService.findAll();
        map.put("tagList",tagList);
        map.put("bookWhereList",bookWhereList);
        map.put("sIsbn",isbn);
        map.put("bookMessage",bookMessage);
        map.put("newMobookId",keyId);
        return new ModelAndView(MAVUriConstant.INVEN_INDEX,map);
    }

}
