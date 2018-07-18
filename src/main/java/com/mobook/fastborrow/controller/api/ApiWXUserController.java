package com.mobook.fastborrow.controller.api;

import com.mobook.fastborrow.constant.RedisConstant;
import com.mobook.fastborrow.constant.WXLogMsgConstant;
import com.mobook.fastborrow.dataobject.BookMessage;
import com.mobook.fastborrow.dataobject.Collection;
import com.mobook.fastborrow.dataobject.Recommended;
import com.mobook.fastborrow.enums.CollectionStatusEnum;
import com.mobook.fastborrow.service.BookMessageService;
import com.mobook.fastborrow.service.CollectionService;
import com.mobook.fastborrow.service.RecommendedService;
import com.mobook.fastborrow.service.UserService;
import com.mobook.fastborrow.utils.ResultVOUtil;
import com.mobook.fastborrow.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 11:33 2018\7\18 0018
 */
@RestController
@RequestMapping("/api/v1/user")
@Slf4j
public class ApiWXUserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RecommendedService recommendedService;
    @Autowired
    private CollectionService collectionService;
    @Autowired
    private BookMessageService bookMessageService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @PostMapping("/add_one_collection")
    public ResultVO addCollection(@RequestHeader("token") String token
                                    , @RequestParam("bookId") String bookId){
        //检查参数
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(bookId)){
            return ResultVOUtil.error(WXLogMsgConstant.WX_PARAM_CODE,WXLogMsgConstant.WX_PARAM);
        }
        //检查Token并获取token对应的用户id
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.WX_TONEKN_PREFIX,token));
        BookMessage bookMessage = bookMessageService.findOne(bookId);
        if (bookMessage != null){
            Collection item = collectionService.findByUserIdAndIsbn(Integer.parseInt(tokenValue),bookMessage.getIsbn(),CollectionStatusEnum.COLLECTION.getCode());
            if (item == null){
                Collection collection = new Collection();
                collection.setUserId(Integer.parseInt(tokenValue));
                collection.setIsbn(bookMessage.getIsbn());
                collection.setColStatus(CollectionStatusEnum.COLLECTION.getCode());
                collectionService.save(collection);
                return ResultVOUtil.success(WXLogMsgConstant.WX_SUCCESS);
            }else{
                return ResultVOUtil.error(WXLogMsgConstant.WX_REPEAT_ADD_CODE,WXLogMsgConstant.WX_REPEAT_ADD);
            }
        }else{
            return ResultVOUtil.error(WXLogMsgConstant.WX_PARAM_CODE,WXLogMsgConstant.WX_PARAM);
        }
    }

    @PostMapping("/add_one_library")
    public ResultVO addLibrary(@RequestHeader("token") String token
            , @RequestParam("bookId") String bookId){
        //检查参数
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(bookId)){
            return ResultVOUtil.error(WXLogMsgConstant.WX_PARAM_CODE,WXLogMsgConstant.WX_PARAM);
        }
        //检查Token并获取token对应的用户id
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.WX_TONEKN_PREFIX,token));
        BookMessage bookMessage = bookMessageService.findOne(bookId);
        if (bookMessage != null){
            Collection item = collectionService.findByUserIdAndIsbn(Integer.parseInt(tokenValue),bookMessage.getIsbn(),CollectionStatusEnum.LIBRARY.getCode());
            if (item == null){
                Collection collection = new Collection();
                collection.setUserId(Integer.parseInt(tokenValue));
                collection.setIsbn(bookMessage.getIsbn());
                collection.setColStatus(CollectionStatusEnum.LIBRARY.getCode());
                collectionService.save(collection);
                return ResultVOUtil.success(WXLogMsgConstant.WX_SUCCESS);
            }else{
                return ResultVOUtil.error(WXLogMsgConstant.WX_REPEAT_ADD_CODE,WXLogMsgConstant.WX_REPEAT_ADD);
            }
        }else{
            return ResultVOUtil.error(WXLogMsgConstant.WX_PARAM_CODE,WXLogMsgConstant.WX_PARAM);
        }
    }

    @PostMapping("/add_more_collection")
    public ResultVO addMoreCollection(@RequestHeader("token") String token
            , @RequestParam("recId") String recId){
        //检查参数
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(recId)){
            return ResultVOUtil.error(WXLogMsgConstant.WX_PARAM_CODE,WXLogMsgConstant.WX_PARAM);
        }
        //检查Token并获取token对应的用户id
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.WX_TONEKN_PREFIX,token));
        Recommended recommended = recommendedService.findOne(Integer.parseInt(recId));
        if (recommended == null){
            return ResultVOUtil.error(WXLogMsgConstant.WX_PARAM_CODE,WXLogMsgConstant.WX_PARAM);
        }else {
            List<BookMessage> bookMessageList = bookMessageService.findByRecNum(Integer.parseInt(recId));
            for(BookMessage item:bookMessageList){
                Collection collection = collectionService.findByUserIdAndIsbn(Integer.parseInt(tokenValue),item.getIsbn(),CollectionStatusEnum.COLLECTION.getCode());
                if (collection != null){//已添加过
                    continue;
                }else{
                    Collection collection2 = new Collection();
                    collection2.setUserId(Integer.parseInt(tokenValue));
                    collection2.setIsbn(item.getIsbn());
                    collection2.setColStatus(CollectionStatusEnum.COLLECTION.getCode());
                    collectionService.save(collection2);
                }
            }
            return ResultVOUtil.success(WXLogMsgConstant.WX_SUCCESS);
        }
    }

    @PostMapping("/add_more_library")
    public ResultVO addMoreLibrary(@RequestHeader("token") String token
            , @RequestParam("recId") String recId){
        //检查参数
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(recId)){
            return ResultVOUtil.error(WXLogMsgConstant.WX_PARAM_CODE,WXLogMsgConstant.WX_PARAM);
        }
        //检查Token并获取token对应的用户id
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.WX_TONEKN_PREFIX,token));
        Recommended recommended = recommendedService.findOne(Integer.parseInt(recId));
        if (recommended == null){
            return ResultVOUtil.error(WXLogMsgConstant.WX_PARAM_CODE,WXLogMsgConstant.WX_PARAM);
        }else {
            List<BookMessage> bookMessageList = bookMessageService.findByRecNum(Integer.parseInt(recId));
            for(BookMessage item:bookMessageList){
                Collection collection = collectionService.findByUserIdAndIsbn(Integer.parseInt(tokenValue),item.getIsbn(),CollectionStatusEnum.LIBRARY.getCode());
                if (collection != null){//已添加过
                    continue;
                }else{
                    Collection collection2 = new Collection();
                    collection2.setUserId(Integer.parseInt(tokenValue));
                    collection2.setIsbn(item.getIsbn());
                    collection2.setColStatus(CollectionStatusEnum.LIBRARY.getCode());
                    collectionService.save(collection2);
                }
            }
            return ResultVOUtil.success(WXLogMsgConstant.WX_SUCCESS);
        }
    }

}
