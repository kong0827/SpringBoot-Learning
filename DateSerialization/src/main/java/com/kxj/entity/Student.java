package com.kxj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author xiangjin.kong
 * @date 2021/1/27 15:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private String name;

    private LocalDateTime birthday;

    private Date date;
}
