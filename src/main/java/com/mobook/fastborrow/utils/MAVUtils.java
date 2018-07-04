package com.mobook.fastborrow.utils;

import com.mobook.fastborrow.constant.MapMsgConstant;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 21:26 2018\6\12 0012
 */
public class MAVUtils {

    public static ModelAndView setResultMOV(String uri,String msg,String url){
        Map<String,Object> map = new HashMap<>();
        map.put(MapMsgConstant.MSG, msg);
        map.put(MapMsgConstant.URL, url);
        return new ModelAndView(uri,map);
    }

    public static ModelAndView setLoginResultMOV(String uri,String namemsg,String passwordmsg){
        Map<String,Object> map = new HashMap<>();
        if (namemsg != null) {
            map.put("namemsg",namemsg);
        }
        if (passwordmsg != null){
            map.put("passwordmsg",passwordmsg);
        }
        return new ModelAndView(uri,map);
    }
}
