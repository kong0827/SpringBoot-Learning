package com.kxj;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author kxj
 * @date 2020/4/21 23:36
 * @desc
 */

// 开启消息队列监听
@EnableRabbit
@SpringBootApplication
public class MQApplication {
    public static void main(String[] args) {
        SpringApplication.run(MQApplication.class, args);
    }
}
