package com.kxj.entity;

import lombok.*;

import javax.persistence.*;

/**
 * @author xiangjin.kong
 * @date 2020/7/2 17:08
 * @desc
 */

@Data
@ToString
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Integer age;

    private String address;
}
