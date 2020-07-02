package com.kxj.entity;

import lombok.*;

/**
 * @author xiangjin.kong
 * @date 2020/7/2 17:08
 * @desc
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    private Integer id;

    private String name;

    private Integer age;

    private String address;
}
