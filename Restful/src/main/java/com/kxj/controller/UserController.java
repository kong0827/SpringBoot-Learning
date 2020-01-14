package com.kxj.controller;

import com.kxj.entity.User;
import com.kxj.entity.UserQueryCondition;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kxj
 * @date 2019/12/27 0:06
 * @Desc
 */
@RestController
public class UserController {

    @GetMapping("/user")
    public List query(UserQueryCondition userQueryCondition) {
//        ReflectionToStringBuilder
        System.out.println(userQueryCondition);
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        users.add(new User());

        return users;
    }
}
