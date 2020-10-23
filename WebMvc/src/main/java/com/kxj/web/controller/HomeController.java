package com.kxj.web.controller;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author kxj
 * @date 2020/4/1 23:53
 * @desc
 */
@RestController
public class HomeController {

    @RequestMapping("/home")
    public void home(Date path) {
        System.out.println(path);
    }

    @GetMapping("/home1")
    public void home1() {
        System.out.println("home1");
    }

    @GetMapping("/home2")
    public void home2() {
        System.out.println("home2");
    }
}
