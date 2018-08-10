package com.mobook.fastborrow.utils;

import com.alibaba.fastjson.JSON;
import com.mobook.fastborrow.dto.Param;
import com.mobook.fastborrow.dto.QueryExDTO;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 15:32 2018\8\9 0009
 */
public class ExpressUtilTest {

    @Test
    public void Test() throws Exception {
        String num = "800939958914062887";
        //配置param
        Param param = new Param("yuantong","800939958914062887");
        //生成签名
        String sign = ExpressUtil.getSign(param);
        //快递100实时查询请求
        String result = ExpressUtil.form(sign,param);
        //输出打印请求结果
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
                for (QueryExDTO queryExDTO: queryExDTOS){
                    System.out.println(queryExDTO.toString());
                }
            }
        }
    }

}