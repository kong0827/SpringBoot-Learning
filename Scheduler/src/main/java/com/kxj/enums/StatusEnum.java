package com.kxj.enums;

import javafx.scene.input.KeyCodeCombination;

/**
 * @author xiangjin.kong
 * @date 2020/10/23 17:30
 */
public enum  StatusEnum {
    ENABLED(1),

    DISABLED(2);

    private Integer code;

    StatusEnum(int code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
