package com.kxj.entity;

import lombok.Data;

import org.hibernate.validator.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @author kxj
 * @date 2019/12/27 0:08
 * @Desc
 */
@Data
public class User implements Serializable {
    @NotBlank(message = "不能爲空")
    private Integer id;
    private String username;
    private int age;
    private Date birthday;
}
