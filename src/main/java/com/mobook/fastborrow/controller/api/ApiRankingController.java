package com.mobook.fastborrow.controller.api;

import com.mobook.fastborrow.converter.BookMessage2RankingVOConverter;
import com.mobook.fastborrow.dataobject.BookMessage;
import com.mobook.fastborrow.dataobject.Inventory;
import com.mobook.fastborrow.enums.BookStatusEnum;
import com.mobook.fastborrow.enums.InventoryStatusEnum;
import com.mobook.fastborrow.service.BookMessageService;
import com.mobook.fastborrow.service.InventoryService;
import com.mobook.fastborrow.utils.ResultVOUtil;
import com.mobook.fastborrow.vo.RankingVO;
import com.mobook.fastborrow.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 16:24 2018\7\17 0017
 */
@RestController
@RequestMapping("/api/v1/ranking")
@Slf4j
public class ApiRankingController {

    @Autowired
    private BookMessageService bookMessageService;
    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/list")
    public ResultVO<List<RankingVO>> rankingList(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                                 @RequestParam(value = "size",defaultValue = "10") Integer size,
                                                 @RequestParam(value = "tagNum") Integer tagNum){
        Sort sort = new Sort(Sort.Direction.DESC,"num");
        Pageable pageable = new PageRequest(page-1,size,sort);
        Page<BookMessage> bookMessageList = bookMessageService.findByStatusIsNotAAndTagNum(BookStatusEnum.DOWN.getCode(),tagNum,pageable);
        List<Integer> integerList = bookMessageList.stream().map(e ->
                just2InventoryState(e)
        ).collect(Collectors.toList());
        List<RankingVO> rankingVOList = BookMessage2RankingVOConverter.convert(bookMessageList,integerList);
        return ResultVOUtil.success(rankingVOList);
    }

    public Integer just2InventoryState(BookMessage bookMessage){
        Inventory inventory = inventoryService.findByIsbn(bookMessage.getIsbn());
        if (inventory.getStatusNum() > 0){
            return InventoryStatusEnum.YES.getCode();
        }else{
            return InventoryStatusEnum.NO.getCode();
        }
    }

}
