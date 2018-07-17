package com.mobook.fastborrow.controller.api;

import com.mobook.fastborrow.converter.Recommended2RecommendVOConverter;
import com.mobook.fastborrow.dataobject.Recommended;
import com.mobook.fastborrow.enums.RecommendedStatusEnum;
import com.mobook.fastborrow.service.RecommendedService;
import com.mobook.fastborrow.utils.ResultVOUtil;
import com.mobook.fastborrow.vo.RecommendVo;
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


/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 14:47 2018\7\17 0017
 */
@RestController
@RequestMapping("/api/v1/recommend")
@Slf4j
public class ApiRecommendedController {

    @Autowired
    private RecommendedService recommendedService;

    @GetMapping("/list")
    public ResultVO<List<RecommendVo>> getList(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                               @RequestParam(value = "size",defaultValue = "6") Integer size){
        Sort sort = new Sort(Sort.Direction.DESC,"createTime");
        Pageable pageable = new PageRequest(page - 1,size,sort);
        Page<Recommended> recommendedPage = recommendedService.findAllByStatus(RecommendedStatusEnum.UP.getCode(),pageable);
        List<RecommendVo> recommendVoList = Recommended2RecommendVOConverter.convert(recommendedPage);
        return ResultVOUtil.success(recommendVoList);
    }

}
