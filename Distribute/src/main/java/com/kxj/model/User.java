package com.kxj.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author xiangjin.kong
 * @date 2021/1/26 17:21
 */
@Data
@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer sex;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
