package com.mobook.fastborrow.controller.api;

import com.mobook.fastborrow.constant.RedisConstant;
import com.mobook.fastborrow.constant.WXLogMsgConstant;
import com.mobook.fastborrow.converter.BookMessage2SearchVOConverter;
import com.mobook.fastborrow.dataobject.BookMessage;
import com.mobook.fastborrow.dataobject.User;
import com.mobook.fastborrow.enums.BookStatusEnum;
import com.mobook.fastborrow.service.BookMessageService;
import com.mobook.fastborrow.service.UserService;
import com.mobook.fastborrow.utils.ResultVOUtil;
import com.mobook.fastborrow.vo.ResultVO;
import com.mobook.fastborrow.vo.SearchVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 11:27 2018\7\20 0020
 */
@RestController
@RequestMapping("/api/v1/search")
@Slf4j
public class ApiSearchController {

    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private BookMessageService bookMessageService;

    @PostMapping("/send")
    public ResultVO send(@RequestHeader("token") String token,@RequestParam("msg") String msg){
        //检查参数
        if (StringUtils.isEmpty(msg)){
            return ResultVOUtil.error(WXLogMsgConstant.WX_PARAM_CODE,WXLogMsgConstant.WX_PARAM);
        }
        List<BookMessage> bookMessageList1 = bookMessageService.findByBookNameIsLikeAndStatusIsNot(msg, BookStatusEnum.DOWN.getCode());
//        List<BookMessage> bookMessageList2 = bookMessageService.findByInfoIsLikeAndStatusIsNot(msg, BookStatusEnum.DOWN.getCode());
        List<BookMessage> bookMessageList3 = bookMessageService.findByAuthorIsLikeAndStatusIsNot(msg, BookStatusEnum.DOWN.getCode());
        List<BookMessage> resultList = race(bookMessageList1,null,bookMessageList3);
        //用户是否登录状态分类
        if (StringUtils.isEmpty(token)){
            //用户未登录---直接查询
        }else{
            //用户登录---查询后去redis缓存中查找，累加。超过7条就删除最久的一个
            //检查Token并获取token对应的用户id
            String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.WX_TONEKN_PREFIX,token));
            User user = userService.findOne(Integer.parseInt(tokenValue));
            String userSearchValue =  redisTemplate.opsForValue().get(String.format(RedisConstant.WX_USER_SEARCH,user.getUserId()));
            if (StringUtils.isEmpty(userSearchValue)){
                //第一次
                saveToRedis(user.getUserId(),msg);
            }else{
                if (com.mobook.fastborrow.utils.StringUtils.getStringSearchSize(userSearchValue) > 0 &&
                        com.mobook.fastborrow.utils.StringUtils.getStringSearchSize(userSearchValue) < 7){
                    System.err.println(com.mobook.fastborrow.utils.StringUtils.varity(userSearchValue,msg));
                    if (com.mobook.fastborrow.utils.StringUtils.varity(userSearchValue,msg)){
                        userSearchValue += "," + msg;
                        saveToRedis(user.getUserId(),userSearchValue);
                    }
                }else if(com.mobook.fastborrow.utils.StringUtils.getStringSearchSize(userSearchValue) >= 7){
                    if (com.mobook.fastborrow.utils.StringUtils.varity(userSearchValue,msg)){
                        userSearchValue = com.mobook.fastborrow.utils.StringUtils.deleteOne(userSearchValue);
                        userSearchValue += "," + msg;
                        saveToRedis(user.getUserId(),userSearchValue);
                    }
                }
            }
        }
        List<SearchVO> searchVOList = BookMessage2SearchVOConverter.convert(resultList);
        return ResultVOUtil.success(searchVOList);
    }

    private List<BookMessage> race(List<BookMessage> bookMessageList1,List<BookMessage> bookMessageList2,List<BookMessage> bookMessageList3) {
        List<BookMessage> items = new ArrayList<BookMessage>();
        List<String> isbns = new ArrayList<String>();
        String isbn = "";
//        bookMessageList1.addAll(bookMessageList2);
        bookMessageList1.addAll(bookMessageList3);
        for (BookMessage item:bookMessageList1){
            if (item.getIsbn().equals(isbn)){
                continue;
            }else{
                isbns.add(item.getIsbn());
                isbn = item.getIsbn();
            }
        }
        HashSet h = new HashSet(isbns);
        isbns.clear();
        isbns.addAll(h);
        for (String isbnItem:isbns){
            items.add(bookMessageService.findByIsbn(isbnItem).get(0));
        }
        return items;
    }


    private void saveToRedis(Integer userId,String msg) {
        Integer expire = RedisConstant.WX_USER_EXPIRE;
        redisTemplate.opsForValue().set(String.format(RedisConstant.WX_USER_SEARCH ,userId),msg,expire, TimeUnit.MINUTES);
    }


}
