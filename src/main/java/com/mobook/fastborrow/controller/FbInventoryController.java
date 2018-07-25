package com.mobook.fastborrow.controller;

import com.mobook.fastborrow.constant.MAVUriConstant;
import com.mobook.fastborrow.constant.URLConstant;
import com.mobook.fastborrow.dataobject.BookMessage;
import com.mobook.fastborrow.dataobject.BookWhere;
import com.mobook.fastborrow.dataobject.Inventory;
import com.mobook.fastborrow.dataobject.Tag;
import com.mobook.fastborrow.enums.InventoryStatusEnum;
import com.mobook.fastborrow.service.BookMessageService;
import com.mobook.fastborrow.service.BookWhereService;
import com.mobook.fastborrow.service.InventoryService;
import com.mobook.fastborrow.service.TagService;
import com.mobook.fastborrow.utils.KeyUtil;
import com.mobook.fastborrow.utils.MAVUtils;
import com.mobook.fastborrow.vo.InventorieBackVO;
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
import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                             Map<String, Object> map){
        PageRequest request = new PageRequest(page - 1,size);
//        Page<Inventory> inventoriePage = null;
//        if (!StringUtils.isEmpty(isbn)){
//            inventoriePage = inventoryService.findByIsbnISLike(isbn,request);
//        }else{
//            inventoriePage = inventoryService.findAll(request);
//        }
        Page<BookMessage> bookMessageList = bookMessageService.findAll(request);
        List<InventorieBackVO> inventorieBackVOList = change2BackVO(bookMessageList);
        map.put("bookMessageList",bookMessageList);
        map.put("inventorieBackVOList",inventorieBackVOList);
        map.put("currentPage", page);
        map.put("size", size);
        map.put("sIsbn",isbn);
        return new ModelAndView(MAVUriConstant.INVEN_LIST,map);
    }

    private List<InventorieBackVO> change2BackVO(Page<BookMessage> inventoriePage) {
        return inventoriePage.stream().map(e ->
            chang(e)
        ).collect(Collectors.toList());
    }

    private InventorieBackVO chang(BookMessage e) {
        List<Inventory> inventoryList = inventoryService.findByIsbn(e.getIsbn());
        int numIn = 0;
        int numOut = 0;
        for (Inventory inventory:inventoryList){
            if (inventory.getStatus() == InventoryStatusEnum.IN.getCode()){
                 numIn += 1;
            }else{
                numOut +=1;
            }
        }
        InventorieBackVO item = new InventorieBackVO();
        item.setIsbn(e.getIsbn());
        item.setBookName(e.getBookName());
        item.setNumIn(numIn);
        item.setNumOut(numOut);
        item.setSum(inventoryList.size());
        return item;
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "isbn",required = false) String isbn,
                              Map<String,Object> map){
        BookMessage bookMessage = new BookMessage();
        //isbn唯一查询
        if (!StringUtils.isEmpty(isbn) && bookMessageService.countByIsbn(isbn)>0){
            BookMessage Item = bookMessageService.findOne(isbn);
            BeanUtils.copyProperties(Item,bookMessage);
        }else if (!StringUtils.isEmpty(isbn) && bookMessageService.countByIsbn(isbn) == 0){
            return MAVUtils.setResultMOV(MAVUriConstant.ERROR,"不存在此ISBN",
                    URLConstant.BASE+URLConstant.INVEN_INDEX);
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
            Inventory inventory = new Inventory();
            bookMessage = bookMessageService.findOne(isbn);
            keyId = KeyUtil.getMobookKey(bookMessage.getTagNum());
            inventory.setMobookId(keyId);
            inventory.setIsbn(isbn);
            inventory.setWhereTag(whereTag);
            inventory.setStatus(InventoryStatusEnum.IN.getCode());
            inventoryService.save(inventory);
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
