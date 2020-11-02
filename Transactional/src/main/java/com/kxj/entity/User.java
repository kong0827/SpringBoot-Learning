package com.kxj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xiangjin.kong
 * @date 2020/11/2 14:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String userName;

    public User(String userName) {
        this.userName = userName;
    }
}
