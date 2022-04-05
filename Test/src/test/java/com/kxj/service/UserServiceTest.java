package com.kxj.service;

import com.kxj.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiangjin.kong
 * @date 2021/6/10 14:10
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    RestTemplate restTemplate;

    List<User> users = new ArrayList<>();

    @Before
    public void initData() {

        User user1 = new User(1, "Jack", 1);
        User user2 = new User(2, "Lucy", 0);
        User user3 = new User(3, "Marry", 0);
        User user4 = new User(4, "Tom", 1);

    }

    @Test
    public void test() throws InterruptedException {
        Object response = restTemplate.getForObject("http://rbox.ruigushop.com/after-sales-order/init/out-dealer-id?access_token=a6HEqpDQyZrPL1ZlMBgPU11zL", Object.class);
        System.out.println(response);


    }
}
