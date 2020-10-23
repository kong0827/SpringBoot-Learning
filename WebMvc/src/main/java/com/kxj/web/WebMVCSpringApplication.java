package com.kxj.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author kxj
 * @date 2020/4/1 22:35
 * @desc
 */
@SpringBootApplication
@EnableAsync
public class WebMVCSpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebMVCSpringApplication.class, args);
    }
}
