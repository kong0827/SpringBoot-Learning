package com.kxj.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.kxj.validator.MyConstraint;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Past;
import java.io.Serializable;
import java.util.Date;

/**
 * @author kxj
 * @date 2020/2/13 17:38
 * @Desc
 */
@Data
public class Student implements Serializable {

    public interface simpleView {
    };

    public interface detailView extends simpleView {
    };

    private Integer id;

    @Past(message = "生日必须是过去的日期")
    private Date birthday;


    @MyConstraint
    private String username;

    @NotBlank(message = "密码不能为空")
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
