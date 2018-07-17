package com.mobook.fastborrow.converter;

import com.mobook.fastborrow.dataobject.Recommended;
import com.mobook.fastborrow.vo.RecomBookVO;
import com.mobook.fastborrow.vo.RecommendDetailVO;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 15:34 2018\7\17 0017
 */
public class Recommended2RecommendDetailVOConverter {

    public static RecommendDetailVO convert(Recommended recommended, List<RecomBookVO> recomBookVOList){
        RecommendDetailVO recommendDetailVO = new RecommendDetailVO();
        BeanUtils.copyProperties(recommended,recommendDetailVO);
        recommendDetailVO.setRecomBookVOList(recomBookVOList);
        return recommendDetailVO;
    }

}
