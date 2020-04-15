package com.kxj.controller;

import com.kxj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author kxj
 * @date 2020/4/15 23:07
 * @desc
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/user")
    public Object User() {

        //线程池个数，一般建议是CPU内核数 或者 CPU内核数据*2
        ExecutorService executorService = Executors.newFixedThreadPool(8);

        /**
         * 测试并发情况下，缓存击穿问题
         */
        for (int i = 0; i < 1000; i++) {
            executorService.submit(() -> {
                userService.getUser(1);
            });
        }

        return userService.getUser(1);
    }

    @GetMapping(value = "/user1")
    public Object User1() {

        //线程池个数，一般建议是CPU内核数 或者 CPU内核数据*2
        ExecutorService executorService = Executors.newFixedThreadPool(8);

        /*
         * 测试并发情况下，缓存击穿问题
         */
        for (int i = 0; i < 1000; i++) {
            executorService.submit(() -> {
                userService.getUser1(1);
            });
        }

        return userService.getUser1(1);
    }


    @GetMapping(value = "/user2")
    public Object User2() {

        return userService.getUser1(1);
    }
}
