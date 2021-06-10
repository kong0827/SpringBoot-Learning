package com.kxj.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author xiangjin.kong
 * @date 2021/6/10 14:04
 */
@Data
public class User {

    private Integer id;
    private String name;
    private Integer sex;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
