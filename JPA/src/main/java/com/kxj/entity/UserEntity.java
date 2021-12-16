package com.kxj.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author xiangjin.kong
 * @date 2021/12/16 11:40
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "real_name")
    private String realName;

    @Column(name = "pass_word")
    private String passWord;

    @Column(name = "user_name")
    private String userName;

    public UserEntity(Integer id, String realName) {
        this.id = id;
        this.realName = realName;
    }
}
