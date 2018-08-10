package com.mobook.fastborrow.controller.api;

import com.mobook.fastborrow.constant.MAVUriConstant;
import com.mobook.fastborrow.constant.RedisConstant;
import com.mobook.fastborrow.constant.URLConstant;
import com.mobook.fastborrow.constant.WXLogMsgConstant;
import com.mobook.fastborrow.converter.LogisticsList2WxLogisticsVOConverter;
import com.mobook.fastborrow.dataobject.*;
import com.mobook.fastborrow.enums.*;
import com.mobook.fastborrow.form.WxLogisticsForm;
import com.mobook.fastborrow.service.*;
import com.mobook.fastborrow.utils.MAVUtils;
import com.mobook.fastborrow.utils.ResultVOUtil;
import com.mobook.fastborrow.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private LogisticsService logisticsService;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private OrderMasterService orderMasterService;

    @GetMapping("/user_wallet")
    public ResultVO userWallet(@RequestHeader("token") String token){
        //检查参数
        if (StringUtils.isEmpty(token)){
            return ResultVOUtil.error(WXLogMsgConstant.WX_PARAM_CODE,WXLogMsgConstant.WX_PARAM);
        }
        //检查Token并获取token对应的用户id
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.WX_TONEKN_PREFIX,token));
        User user = userService.findOne(Integer.parseInt(tokenValue));
        WxWalletVO wxWalletVO = new WxWalletVO();
        BeanUtils.copyProperties(user,wxWalletVO);
        return ResultVOUtil.success(wxWalletVO);
    }


    @GetMapping("/user_home")
    public ResultVO userHome(@RequestHeader("token") String token){
        //检查参数
        if (StringUtils.isEmpty(token)){
            return ResultVOUtil.error(WXLogMsgConstant.WX_PARAM_CODE,WXLogMsgConstant.WX_PARAM);
        }
        //检查Token并获取token对应的用户id
        WxUserLoginVO wxUserLoginVO = new WxUserLoginVO();
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.WX_TONEKN_PREFIX,token));
        User user = userService.findOne(Integer.parseInt(tokenValue));
        BeanUtils.copyProperties(user,wxUserLoginVO);
        OrderMaster orderMaster = orderMasterService.findByBuyerOpenidAndOrderStatusIsNot(user.getOpenId(), OrderStatusEnum.PENDPAY.getCode());
        if (orderMaster == null){
            wxUserLoginVO.setStatus(0);
        }else{
            wxUserLoginVO.setStatus(orderMaster.getOrderStatus());
        }
        return ResultVOUtil.success(wxUserLoginVO);
    }

    @PostMapping("/add_one_collection")
    public ResultVO addCollection(@RequestHeader("token") String token
                                    , @RequestParam("bookId") String bookId){
        //检查参数
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(bookId)){
            return ResultVOUtil.error(WXLogMsgConstant.WX_PARAM_CODE,WXLogMsgConstant.WX_PARAM);
        }
        //检查Token并获取token对应的用户id
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.WX_TONEKN_PREFIX,token));
        User user = userService.findOne(Integer.parseInt(tokenValue));
        BookMessage bookMessage = bookMessageService.findOne(bookId);
        if (bookMessage != null){
            Collection item = collectionService.findByUserIdAndIsbn(Integer.parseInt(tokenValue),bookMessage.getIsbn(),CollectionStatusEnum.COLLECTION.getCode());
            if (item == null){
                Collection collection = new Collection();
                collection.setUserId(Integer.parseInt(tokenValue));
                collection.setIsbn(bookMessage.getIsbn());
                collection.setColStatus(CollectionStatusEnum.COLLECTION.getCode());
                collectionService.save(collection);
                user.setCollectionNum(collectionService.countByUserIdAndColStatus(user.getUserId(),CollectionStatusEnum.COLLECTION.getCode()));
                userService.save(user);
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
        User user = userService.findOne(Integer.parseInt(tokenValue));
        BookMessage bookMessage = bookMessageService.findOne(bookId);
        if (bookMessage != null){
            Collection item = collectionService.findByUserIdAndIsbn(Integer.parseInt(tokenValue),bookMessage.getIsbn(),CollectionStatusEnum.LIBRARY.getCode());
            if (item == null){
                Collection collection = new Collection();
                collection.setUserId(Integer.parseInt(tokenValue));
                collection.setIsbn(bookMessage.getIsbn());
                collection.setColStatus(CollectionStatusEnum.LIBRARY.getCode());
                collectionService.save(collection);
                user.setLibraryNum(collectionService.countByUserIdAndColStatus(user.getUserId(),CollectionStatusEnum.LIBRARY.getCode()));
                userService.save(user);
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
        User user = userService.findOne(Integer.parseInt(tokenValue));
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
            user.setCollectionNum(collectionService.countByUserIdAndColStatus(user.getUserId(),CollectionStatusEnum.COLLECTION.getCode()));
            userService.save(user);
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
        User user = userService.findOne(Integer.parseInt(tokenValue));
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
            user.setLibraryNum(collectionService.countByUserIdAndColStatus(user.getUserId(),CollectionStatusEnum.LIBRARY.getCode()));
            userService.save(user);
            return ResultVOUtil.success(WXLogMsgConstant.WX_SUCCESS);
        }
    }

    /**
     * 借书库
     * @param token
     * @return
     */
    @GetMapping("/collection_list")
    public ResultVO collectionList(@RequestHeader("token") String token){
        //检查参数
        if (StringUtils.isEmpty(token)){
            return ResultVOUtil.error(WXLogMsgConstant.WX_PARAM_CODE,WXLogMsgConstant.WX_PARAM);
        }
        //检查Token并获取token对应的用户id
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.WX_TONEKN_PREFIX,token));
        List<Collection> collectionList = collectionService.findByUserIdAndColStatus(Integer.parseInt(tokenValue),
                CollectionStatusEnum.COLLECTION.getCode());
        List<WxCollectionDetailVO> wxCollectionDetailVOList = collectionList.stream().map(e ->
                change2WxCollectionDetailVO(e)
        ).collect(Collectors.toList());
        return ResultVOUtil.success(wxCollectionDetailVOList);
    }

    /**
     * 藏书阁
     * @param token
     * @return
     */
    @GetMapping("/library_list")
    public ResultVO libraryList(@RequestHeader("token") String token){
        //检查参数
        if (StringUtils.isEmpty(token)){
            return ResultVOUtil.error(WXLogMsgConstant.WX_PARAM_CODE,WXLogMsgConstant.WX_PARAM);
        }
        //检查Token并获取token对应的用户id
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.WX_TONEKN_PREFIX,token));
        List<Collection> collectionList = collectionService.findByUserIdAndColStatus(Integer.parseInt(tokenValue),
                CollectionStatusEnum.LIBRARY.getCode());
        List<WxLibraryDetailVO> wxLibraryDetailVOList = collectionList.stream().map(e ->
                change2WxLibraryDetailVO(e)
        ).collect(Collectors.toList());
        return ResultVOUtil.success(wxLibraryDetailVOList);
    }

    /**
     * 单个删除（藏书阁、借书库）
     * @param token
     * @param isbn
     * @param state
     * @return
     */
    @PostMapping("/delete_one")
    public ResultVO deleteByCollectionOrLibrary(@RequestHeader("token") String token,
                                                @RequestParam("isbn") String isbn,
                                                @RequestParam("state") String state){
        //检查参数
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(isbn) || StringUtils.isEmpty(state)){
            return ResultVOUtil.error(WXLogMsgConstant.WX_PARAM_CODE,WXLogMsgConstant.WX_PARAM);
        }
        //检查Token并获取token对应的用户id
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.WX_TONEKN_PREFIX,token));
        User user = userService.findOne(Integer.parseInt(tokenValue));
        Collection collection = null;
        if (state.equals("1")){//藏书阁
            collection = collectionService.findByUserIdAndIsbn(Integer.parseInt(tokenValue),isbn,CollectionStatusEnum.COLLECTION.getCode());
            collection.setColStatus(CollectionStatusEnum.DOWN.getCode());
            collectionService.save(collection);
            user.setCollectionNum(collectionService.countByUserIdAndColStatus(user.getUserId(),CollectionStatusEnum.COLLECTION.getCode()));
            userService.save(user);
        } else {
            collection = collectionService.findByUserIdAndIsbn(Integer.parseInt(tokenValue),isbn,CollectionStatusEnum.LIBRARY.getCode());
            collection.setColStatus(CollectionStatusEnum.DOWN.getCode());
            collectionService.save(collection);
            user.setLibraryNum(collectionService.countByUserIdAndColStatus(user.getUserId(),CollectionStatusEnum.LIBRARY.getCode()));
            userService.save(user);
        }
        return ResultVOUtil.success(WXLogMsgConstant.WX_SUCCESS);
    }

    @PostMapping("/delete_collection")
    public ResultVO deleteColletion(@RequestHeader("token") String token,@RequestParam("isbns") String[] isbns){
        //检查参数
        if (StringUtils.isEmpty(token) || isbns.length <= 0){
            return ResultVOUtil.error(WXLogMsgConstant.WX_PARAM_CODE,WXLogMsgConstant.WX_PARAM);
        }
        //检查Token并获取token对应的用户id
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.WX_TONEKN_PREFIX,token));
        User user = userService.findOne(Integer.parseInt(tokenValue));
        for (String item : isbns){
            Collection collection = collectionService.findByUserIdAndIsbn(Integer.parseInt(tokenValue),
                    item,CollectionStatusEnum.COLLECTION.getCode());
            collection.setColStatus(CollectionStatusEnum.DOWN.getCode());
            collectionService.save(collection);
        }
        user.setCollectionNum(collectionService.countByUserIdAndColStatus(user.getUserId(),CollectionStatusEnum.COLLECTION.getCode()));
        userService.save(user);
        return ResultVOUtil.success(WXLogMsgConstant.WX_SUCCESS);
    }

    @PostMapping("/add_to_library")
    public ResultVO addToLibrary(@RequestHeader("token") String token,@RequestParam("isbns") String[] isbns){
        //检查参数
        if (StringUtils.isEmpty(token) || isbns.length <= 0){
            return ResultVOUtil.error(WXLogMsgConstant.WX_PARAM_CODE,WXLogMsgConstant.WX_PARAM);
        }
        //检查Token并获取token对应的用户id
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.WX_TONEKN_PREFIX,token));
        User user = userService.findOne(Integer.parseInt(tokenValue));
        //做校验
        for (String item : isbns){
            Collection collectionLibrary = collectionService.findByUserIdAndIsbn(Integer.parseInt(tokenValue),
                    item,CollectionStatusEnum.LIBRARY.getCode());
            if (collectionLibrary != null){
                return ResultVOUtil.success("存在重复图书，请重新选择");
            }
            Collection collection = collectionService.findByUserIdAndIsbn(Integer.parseInt(tokenValue),
                    item,CollectionStatusEnum.COLLECTION.getCode());
            collection.setColStatus(CollectionStatusEnum.LIBRARY.getCode());
            collectionService.save(collection);
        }
        user.setCollectionNum(collectionService.countByUserIdAndColStatus(user.getUserId(),CollectionStatusEnum.COLLECTION.getCode()));
        user.setLibraryNum(collectionService.countByUserIdAndColStatus(user.getUserId(),CollectionStatusEnum.LIBRARY.getCode()));
        userService.save(user);
        return ResultVOUtil.success(WXLogMsgConstant.WX_SUCCESS);
    }

    @PostMapping("/add_log")
    public ResultVO addLog(@RequestHeader("token") String token, @Valid WxLogisticsForm form, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(WXLogMsgConstant.WX_PARAM_CODE,WXLogMsgConstant.WX_PARAM);
        }
        //检查参数
        if (StringUtils.isEmpty(token)){
            return ResultVOUtil.error(WXLogMsgConstant.WX_PARAM_CODE,WXLogMsgConstant.WX_PARAM);
        }
        //检查Token并获取token对应的用户id
        if (form.getLogId() == null){
            String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.WX_TONEKN_PREFIX,token));
            User user = userService.findOne(Integer.parseInt(tokenValue));
            Logistics logistics = new Logistics();
            BeanUtils.copyProperties(form,logistics);
            logistics.setUserId(user.getUserId());
            List<Logistics> logisticsList = logisticsService.findByUserId(user.getUserId());
            if (logisticsList.size() <= 0){
                logistics.setStatus(LogisticsStatusEnum.UP.getCode());
            }else{
                logistics.setStatus(LogisticsStatusEnum.DOWN.getCode());
            }
            logisticsService.save(logistics);
        }else{
            Logistics logistics = logisticsService.findOne(form.getLogId());
            BeanUtils.copyProperties(form,logistics);
            logisticsService.save(logistics);
        }
        return ResultVOUtil.success(WXLogMsgConstant.WX_SUCCESS);
    }

    @PostMapping("/set_log")
    public ResultVO setLog(@RequestHeader("token") String token,@RequestParam("logId") Integer logId){
        //检查参数
        if (StringUtils.isEmpty(token)){
            return ResultVOUtil.error(WXLogMsgConstant.WX_PARAM_CODE,WXLogMsgConstant.WX_PARAM);
        }
        //检查Token并获取token对应的用户id
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.WX_TONEKN_PREFIX,token));
        User user = userService.findOne(Integer.parseInt(tokenValue));
        List<Logistics> logisticsList = logisticsService.findByUserId(user.getUserId());
        for (Logistics item : logisticsList){
            item.setStatus(LogisticsStatusEnum.DOWN.getCode());
            logisticsService.save(item);
        }
        Logistics logistics = logisticsService.findOne(logId);
        logistics.setStatus(LogisticsStatusEnum.UP.getCode());
        logisticsService.save(logistics);
        return ResultVOUtil.success();
    }

    @GetMapping("/list_log")
    public ResultVO getListLog(@RequestHeader("token") String token){
        //检查参数
        if (StringUtils.isEmpty(token)){
            return ResultVOUtil.error(WXLogMsgConstant.WX_PARAM_CODE,WXLogMsgConstant.WX_PARAM);
        }
        //检查Token并获取token对应的用户id
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.WX_TONEKN_PREFIX,token));
        User user = userService.findOne(Integer.parseInt(tokenValue));
        List<Logistics> logisticsList = logisticsService.findByUserId(user.getUserId());
        List<WxLogisticsVO> wxLogisticsVOList = LogisticsList2WxLogisticsVOConverter.convert(logisticsList);
        return ResultVOUtil.success(wxLogisticsVOList);
    }

    @PostMapping("/delete_log")
    public ResultVO deleteLog(@RequestHeader("token") String token,@RequestParam("logId") Integer logId){
        //检查参数
        if (StringUtils.isEmpty(token) || logId == null){
            return ResultVOUtil.error(WXLogMsgConstant.WX_PARAM_CODE,WXLogMsgConstant.WX_PARAM);
        }
        //检查Token并获取token对应的用户id
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.WX_TONEKN_PREFIX,token));
        Logistics logistics = logisticsService.findOne(logId);
        if (logistics != null && logistics.getUserId() == Integer.parseInt(tokenValue)){
            logisticsService.deleteOne(logId);
            return ResultVOUtil.success(WXLogMsgConstant.WX_SUCCESS);
        }else{
            return ResultVOUtil.error(WXLogMsgConstant.WX_PARAM_CODE,WXLogMsgConstant.WX_PARAM);
        }
    }

    @GetMapping("/detail_log")
    public ResultVO detailLog(@RequestHeader("token") String token,@RequestParam("logId") Integer logId){
        //检查参数
        if (StringUtils.isEmpty(token) || logId == null){
            return ResultVOUtil.error(WXLogMsgConstant.WX_PARAM_CODE,WXLogMsgConstant.WX_PARAM);
        }
        //检查Token并获取token对应的用户id
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.WX_TONEKN_PREFIX,token));
        Logistics logistics = logisticsService.findOne(logId);
        if (logistics != null && logistics.getUserId() == Integer.parseInt(tokenValue)){
            return ResultVOUtil.success(logistics);
        }else{
            return ResultVOUtil.error(WXLogMsgConstant.WX_PARAM_CODE,WXLogMsgConstant.WX_PARAM);
        }
    }

    private WxLibraryDetailVO change2WxLibraryDetailVO(Collection e) {
        WxLibraryDetailVO item = new WxLibraryDetailVO();
        BookMessage bookMessage = bookMessageService.findOne(e.getIsbn());
        BeanUtils.copyProperties(bookMessage,item);
        return item;
    }

    private WxCollectionDetailVO change2WxCollectionDetailVO(Collection collection){
        WxCollectionDetailVO item = new WxCollectionDetailVO();
        BookMessage bookMessage = bookMessageService.findOne(collection.getIsbn());
        BeanUtils.copyProperties(bookMessage,item);
        List<Inventory> inventoryList = inventoryService.findByIsbn(item.getIsbn());
        item.setInventoryState(InventoryVOStatusEnum.NO.getCode());
        for (Inventory inventory:inventoryList){
            if (inventory.getStatus() == InventoryStatusEnum.IN.getCode()){
                item.setInventoryState(InventoryVOStatusEnum.Yes.getCode());
            }
        }
        return item;
    }

}
