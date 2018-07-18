package com.mobook.fastborrow.controller;

import com.mobook.fastborrow.constant.MAVUriConstant;
import com.mobook.fastborrow.dataobject.User;
import com.mobook.fastborrow.service.CollectionService;
import com.mobook.fastborrow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 15:28 2018\7\18 0018
 */
@Controller
@RequestMapping("/admin/library")
public class FbLibraryController {

    @Autowired
    private CollectionService collectionService;
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "10") Integer size,
                             Map<String, Object> map){
        Sort sort = new Sort(Sort.Direction.DESC,"libraryNum");
        Pageable pageable = new PageRequest(page-1,size,sort);
        Page<User> userPage = userService.findAll(pageable);
        map.put("userPage",userPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView(MAVUriConstant.LIBRARY_LIST,map);
    }

}
