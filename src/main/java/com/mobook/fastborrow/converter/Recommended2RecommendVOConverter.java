package com.mobook.fastborrow.converter;

import com.mobook.fastborrow.dataobject.Advertising;
import com.mobook.fastborrow.dataobject.Recommended;
import com.mobook.fastborrow.vo.AdvertisingVO;
import com.mobook.fastborrow.vo.RecommendVo;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 14:54 2018\7\17 0017
 */
public class Recommended2RecommendVOConverter {

    public static RecommendVo convert(Recommended recommended){
        RecommendVo recommendVo = new RecommendVo();
        BeanUtils.copyProperties(recommended,recommendVo);
        return recommendVo;
    }

    public static List<RecommendVo> convert(List<Recommended> recommendedList){
        return recommendedList.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());
    }

    public static List<RecommendVo> convert(Page<Recommended> recommendedPage){
        return recommendedPage.stream().map(e ->
            convert(e)
        ).collect(Collectors.toList());
    }

}
