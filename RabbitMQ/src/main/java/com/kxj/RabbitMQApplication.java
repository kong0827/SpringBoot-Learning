package com.kxj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xiangjin.kong
 * @date 2021/2/19 10:25
 * @desc Spring Boot 集成 RabbitMQ
 */
@SpringBootApplication
public class RabbitMQApplication {
    public static void main(String[] args) {
        SpringApplication.run(RabbitMQApplication.class, args);
    }
}
