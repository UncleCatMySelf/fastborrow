package com.mobook.fastborrow.converter;

import com.mobook.fastborrow.dataobject.Advertising;
import com.mobook.fastborrow.vo.AdvertisingVO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 11:18 2018\7\4 0004
 */
public class Advertising2AdvertisingVOConverter {

    public static AdvertisingVO convert(Advertising advertising){
        AdvertisingVO advertisingVO = new AdvertisingVO();
        BeanUtils.copyProperties(advertising,advertisingVO);
        return advertisingVO;
    }

    public static List<AdvertisingVO> convert(List<Advertising> advertisingList){
        return advertisingList.stream().map(e ->
            convert(e)
        ).collect(Collectors.toList());
    }

}
