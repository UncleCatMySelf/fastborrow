package com.mobook.fastborrow.utils;

import com.alibaba.fastjson.JSON;
import com.mobook.fastborrow.constant.ExpressConstant;
import com.mobook.fastborrow.dto.Param;
import com.mobook.fastborrow.dto.QueryDTO;
import com.mobook.fastborrow.dto.QueryExDTO;
import com.mobook.fastborrow.enums.ExpreeStatusEnum;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 15:27 2018\8\9 0009
 */
public class ExpressUtil {

    private static String charset = "UTF-8";

    public static String auto(String num){
        String url = ExpressConstant.NUMBER_ATTRIBUTION_URL + "?num=" + num + "&key=" + ExpressConstant.KEY;
        String result = HttpServiceUtils.sendGet(url);
        return result;
    }

    /**
     * 获取公司编码
     * @param num
     * @return
     */
    public static String getCode(String num){
        String code = auto(num);
        Map codeMaps = JSON.parseObject(code.substring(1,code.length()-1),Map.class);
        for (Object map : codeMaps.entrySet()){
            System.out.println(((Map.Entry)map).getKey()+"     " + ((Map.Entry)map).getValue());
            if (((Map.Entry)map).getKey() == "comCode"){
                return ((Map.Entry)map).getValue().toString();
            }
        }
        return null;
    }

    public static QueryDTO getResult(Param param) throws Exception {
        //配置param
        //Param param = new Param("yuantong","800939958914062887");
        //生成签名
        String sign = getSign(param);
        //快递100实时查询请求
        String result = form(sign,param);
        QueryDTO queryDTO = new QueryDTO();
        Map resultMap = JSON.parseObject(result,Map.class);
        List<QueryExDTO> queryExDTOS = new ArrayList<>();
        for (Object map : resultMap.entrySet()){
            if (((Map.Entry)map).getKey().equals("data")){
                String data = ((Map.Entry)map).getValue().toString().substring(1,((Map.Entry)map).getValue().toString().length()-1);
                String[] datas = data.split("},");
                for (int i = 0; i < datas.length; i++){
                    if (i != datas.length-1){
                        datas[i] = datas[i]+"}";
                    }
                    Map dataMap = JSON.parseObject(datas[i] , Map.class);
                    QueryExDTO queryExDTO = new QueryExDTO();
                    for (Object item : dataMap.entrySet()){
                        if (((Map.Entry)item).getKey().equals("ftime")){
                            queryExDTO.setFtime(((Map.Entry)item).getValue().toString());
                        }
                        if (((Map.Entry)item).getKey().equals("context")){
                            queryExDTO.setContext(((Map.Entry)item).getValue().toString());
                        }
                    }
                    queryExDTOS.add(queryExDTO);
                }
            }
            if (((Map.Entry)map).getKey().equals("state")){
                queryDTO.setState(getStatus(Integer.valueOf(((Map.Entry)map).getValue().toString())));
            }
        }
        queryDTO.setQueryExDTOList(queryExDTOS);
        queryDTO.setNum(param.getNum());
        return queryDTO;
    }

    private static String getStatus(Integer value) {
        switch (value){
            case 0:
                return ExpreeStatusEnum.TRANSIT.getMessage();
            case 1:
                return ExpreeStatusEnum.RANGE_OF_PARTS.getMessage();
            case 2:
                return ExpreeStatusEnum.DIFFICULT.getMessage();
            case 3:
                return ExpreeStatusEnum.SIGN.getMessage();
            case 4:
                return ExpreeStatusEnum.EXIT_SIGN.getMessage();
            case 5:
                return ExpreeStatusEnum.SEND_PIECES.getMessage();
            case 6:
                return ExpreeStatusEnum.RETURN.getMessage();
            default:
                return null;
        }
    }

    public static String form(String sign,Param param) throws Exception {
        String url = ExpressConstant.FORM_URL;
//        String params = "param={\"com\":\"yuantong\",\"num\":\"800939958914062887\",\"from\":\"\",\"to\":\"\",\"resultv2\":0}&sign=2D8AD2C67EE268CF2C906791DA8AA5DC&customer=FD0805ED357C543A6B4C13BD279BC739";
        String params = MakeParams(sign,param);
        String result = HttpServiceUtils.sendPost(url,params);
        return result;
    }

    private static String MakeParams(String sign, Param param) {
        return "param="+StringUtils.replaceBlank(JsonUtil.toJson(param))+"&sign="+sign+"&customer="+ExpressConstant.CUSTOMER;
    }

    public static String getSign(Param param) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String str = StringUtils.replaceBlank(JsonUtil.toJson(param))+ExpressConstant.KEY+ExpressConstant.CUSTOMER;
        String newstr = MD5Util.MD5Encode(str,charset);
        return newstr;
    }

}
