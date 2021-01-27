package com.kxj.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author xiangjin.kong
 * @date 2021/1/27 13:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime birthDay;
}
