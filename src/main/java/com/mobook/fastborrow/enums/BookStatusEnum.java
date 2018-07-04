package com.mobook.fastborrow.enums;

import lombok.Getter;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 16:21 2018\6\30 0030
 */
@Getter
public enum BookStatusEnum  implements CodeEnum{
    UP(0 , "上线"),
    DOWN(1, "下线"),
    READ(3,"上线"),
    NEWBOOK(10,"上线"),
    EDITOR(20,"上线"),
    WINNING(30,"上线"),
    ;
    private Integer code;

    private String message;

    BookStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
