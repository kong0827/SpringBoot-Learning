package com.kxj.service;

import com.kxj.DistributeApplication;
import com.kxj.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author xiangjin.kong
 * @date 2021/1/26 17:24
 */
@SpringBootTest(classes = DistributeApplication.class)
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void getAll() {
        List<User> list = userService.getAll();
        list.forEach(e -> System.out.println(e));
    }

    @Test
    public void save() {
        User user = new User();
        user.setName("小婷");
        user.setSex(0);
        user.setCreateTime(LocalDateTime.now());
        user = userService.save(user);
        System.out.println(user);
    }
}
