package com.kxj.entity;

import com.fasterxml.jackson.annotation.JsonView;

import java.io.Serializable;

/**
 * @author kxj
 * @date 2020/2/13 17:38
 * @Desc
 */
public class Student implements Serializable {

    public interface simpleView {
    };

    public interface detailView extends simpleView {
    };

    private String username;
    private String password;

    @JsonView(simpleView.class)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonView(detailView.class)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
