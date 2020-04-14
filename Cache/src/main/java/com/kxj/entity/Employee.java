package com.kxj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee implements Serializable {

    private Integer id;
    private String lastName;
    private String email;
    private Integer gender; //性别 1男  0女
    private Integer dId;


}
