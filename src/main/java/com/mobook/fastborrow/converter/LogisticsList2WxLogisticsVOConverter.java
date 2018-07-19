package com.mobook.fastborrow.converter;

import com.mobook.fastborrow.dataobject.Advertising;
import com.mobook.fastborrow.dataobject.Logistics;
import com.mobook.fastborrow.vo.AdvertisingVO;
import com.mobook.fastborrow.vo.WxLogisticsVO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 16:07 2018\7\19 0019
 */
public class LogisticsList2WxLogisticsVOConverter {

    public static WxLogisticsVO convert(Logistics logistics){
        WxLogisticsVO wxLogisticsVO = new WxLogisticsVO();
        BeanUtils.copyProperties(logistics,wxLogisticsVO);
        return wxLogisticsVO;
    }

    public static List<WxLogisticsVO> convert(List<Logistics> logisticsList){
        return logisticsList.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());
    }

}
