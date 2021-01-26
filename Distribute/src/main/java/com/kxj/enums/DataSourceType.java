package com.kxj.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xiangjin.kong
 * @date 2021/1/26 16:48
 */
@Getter
@AllArgsConstructor
public enum DataSourceType {

    /**
     * 主
     */
    MASTER,
    /**
     * 从
     */
    SLAVE;


}
