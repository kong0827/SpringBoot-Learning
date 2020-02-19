package com.kxj.controller;

import com.kxj.entity.User;
import com.kxj.entity.UserQueryCondition;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping(value = "/user/{id:\\d+}")
    public User getInfo(@PathVariable(name = "id") String id) {
        User user = new User();
        user.setUsername("tom");
        return user;
    }

    @PostMapping("/user")
    public User create(User user) {
        String s = ReflectionToStringBuilder.toString(user);
        System.out.println(s);

        User users = new User();
        users.setId(1);
        return users;
    }
}
