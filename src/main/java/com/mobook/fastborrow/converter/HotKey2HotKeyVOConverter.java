package com.mobook.fastborrow.converter;

import com.mobook.fastborrow.dataobject.HotKey;
import com.mobook.fastborrow.vo.HotKeyVo;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 15:34 2018\7\9 0009
 */
public class HotKey2HotKeyVOConverter {

    public static HotKeyVo convert(HotKey hotKey){
        HotKeyVo hotKeyVo = new HotKeyVo();
        BeanUtils.copyProperties(hotKey,hotKeyVo);
        return hotKeyVo;
    }

    public static List<HotKeyVo> convert(List<HotKey> hotKeyList){
        return hotKeyList.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());
    }

}
