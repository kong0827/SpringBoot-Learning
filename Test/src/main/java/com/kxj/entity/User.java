package com.kxj.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author xiangjin.kong
 * @date 2021/6/10 14:04
 */
@Data
@NoArgsConstructor
public class User {

    private Integer id;
    private String name;
    private Integer sex;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;


    public User(Integer id, String name, Integer sex) {
        this.id = id;
        this.name = name;
        this.sex = sex;
    }
}
