package com.mobook.fastborrow.service.elastic;

import com.mobook.fastborrow.vo.ResultVO;

/**
 * 检索接口
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 14:59 2018\7\25 0025
 */
public interface ISearchService {

    /**
     * 索引目标图书
     * @param isbn
     */
    void index(String isbn);

    /**
     * 移除图书索引
     * @param isbn
     */
    void remove(String isbn);

    /**
     * 搜索图书接口
     * @param msg
     * @return
     */
    ResultVO query(String msg,int start,int size);

    /**
     * 获取补全建议关键词
     */
    ResultVO suggest(String prefix);

}
