package com.mobook.fastborrow.controller;

import com.mobook.fastborrow.constant.MAVUriConstant;
import com.mobook.fastborrow.dataobject.BookMessage;
import com.mobook.fastborrow.dataobject.Collection;
import com.mobook.fastborrow.dataobject.User;
import com.mobook.fastborrow.enums.CollectionStatusEnum;
import com.mobook.fastborrow.service.BookMessageService;
import com.mobook.fastborrow.service.CollectionService;
import com.mobook.fastborrow.service.UserService;
import com.mobook.fastborrow.vo.CollectionVO;
import org.apache.commons.lang3.StringUtils;
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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Autowired
    private BookMessageService bookMessageService;

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

    @GetMapping("/search")
    public ModelAndView search(@RequestParam(value = "page",defaultValue = "1") Integer page,
                               @RequestParam(value = "size",defaultValue = "10") Integer size,
                               @RequestParam(value = "userId",defaultValue = "2") String userId,
                               Map<String, Object> map){
        PageRequest request = new PageRequest(page - 1,size);
        Page<Collection> collectionPage = null;
        List<CollectionVO> collectionVOList = null;
        if (!StringUtils.isEmpty(userId)){
            collectionPage = collectionService.findByUserIdAndColStatus(Integer.parseInt(userId),
                    CollectionStatusEnum.LIBRARY.getCode(),request);
            collectionVOList = collectionPage.stream().map(e ->
                    getCollectionVO(e)
            ).collect(Collectors.toList());
        }
        map.put("collectionVOList",collectionVOList);
        map.put("collectionPage",collectionPage);
        map.put("currentPage", page);
        map.put("size", size);
        map.put("userId",userId);
        return new ModelAndView(MAVUriConstant.LIBRARY_SEARCH,map);
    }

    public CollectionVO getCollectionVO(Collection collection){
        CollectionVO collectionVO = new CollectionVO();
        collectionVO.setUserId(collection.getUserId());
        collectionVO.setIsbn(collection.getIsbn());
        User user = userService.findOne(collection.getUserId());
        collectionVO.setUserName(user.getUserName());
        BookMessage bookMessage = bookMessageService.findOne(collection.getIsbn());
        collectionVO.setBookName(bookMessage.getBookName());
        return collectionVO;
    }

}
