package com.mobook.fastborrow.controller;

import com.mobook.fastborrow.constant.MAVUriConstant;
import com.mobook.fastborrow.constant.URLConstant;
import com.mobook.fastborrow.dataobject.User;
import com.mobook.fastborrow.service.UserService;
import com.mobook.fastborrow.utils.MAVUtils;
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
 * @Date:Created in 16:30 2018\7\13 0013
 */
@Controller
@RequestMapping("/admin/user")
public class FbUserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "10") Integer size,
                             @RequestParam(value = "userId",defaultValue = "") Integer userId,
                             Map<String, Object> map){
        PageRequest request = new PageRequest(page - 1,size);
        Page<User> userPage = null;
        if (userId != null){
            userPage = userService.findByUserId(userId,request);
            if (userPage.getTotalElements()<=0){
                return MAVUtils.setResultMOV(MAVUriConstant.ERROR,"用户不存在",
                        URLConstant.BASE+URLConstant.USER_LIST);
            }
        }else{
            userPage = userService.findAll(request);
        }
        map.put("userPage",userPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView(MAVUriConstant.USER_LIST,map);
    }

    @GetMapping("/morelist")
    public ModelAndView morelist(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                 @RequestParam(value = "size",defaultValue = "10") Integer size,
                                 @RequestParam(value = "statsInt",defaultValue = "0") Integer statsInt,
                                 @RequestParam(value = "statsBal",defaultValue = "0") Integer statsBal,
                                 @RequestParam(value = "statsDep",defaultValue = "0") Integer statsDep,
                                 Map<String, Object> map){
        PageRequest request = new PageRequest(page - 1,size);
        Page<User> userPage = null;
        if (statsInt == 1 && statsBal == 0 && statsDep == 0){
            //积分降序
            Sort sort = new Sort(Sort.Direction.DESC,"integral");
            Pageable pageable = new PageRequest(page-1,size,sort);
            userPage = userService.findAll(pageable);
        }else if (statsInt == 0 && statsBal == 1 && statsDep == 0){
            //余额降序
            Sort sort = new Sort(Sort.Direction.DESC,"userBalance");
            Pageable pageable = new PageRequest(page-1,size,sort);
            userPage = userService.findAll(pageable);
        }else if (statsInt == 0 && statsBal == 0 && statsDep == 1){
            //押金降序
            Sort sort = new Sort(Sort.Direction.DESC,"userDeposit");
            Pageable pageable = new PageRequest(page-1,size,sort);
            userPage = userService.findAll(pageable);
        }else{
            userPage = userService.findAll(request);
        }
        map.put("userPage",userPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView(MAVUriConstant.USER_MORELIST,map);
    }

}
