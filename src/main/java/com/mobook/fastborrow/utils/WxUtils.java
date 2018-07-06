package com.mobook.fastborrow.utils;

import com.mobook.fastborrow.constant.AppConstant;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 16:02 2018\7\6 0006
 */
public class WxUtils {

    public static JSONArray prepareCacheValue(JSONArray result, Integer userId){
        JSONObject object = new JSONObject();
        object.put("userid",userId);
        object.put("scope", AppConstant.SCOPE_USER);
        result.add(object);
        return result;
    }

}
