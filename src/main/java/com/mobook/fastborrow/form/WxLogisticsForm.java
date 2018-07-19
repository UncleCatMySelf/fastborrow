package com.mobook.fastborrow.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 14:58 2018\7\19 0019
 */
@Data
public class WxLogisticsForm {

    @NotEmpty
    private String logName;

    /**联系方式*/
    @NotEmpty
    private String logPhone;

    /**邮编*/
    private String logZip;

    /**地址*/
    @NotEmpty
    private String logAddress;

    /**区域*/
    @NotEmpty
    private String logRegional;

    private Integer status;

}
