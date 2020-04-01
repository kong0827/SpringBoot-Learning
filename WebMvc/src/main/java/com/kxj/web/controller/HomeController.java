package com.kxj.web.controller;

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
}
