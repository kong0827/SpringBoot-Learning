package com.kxj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author xiangjin.kong
 * @date 2020/9/7 14:19
 */
@SpringBootApplication
@EnableAsync   // 开启异步调用
@EnableTransactionManagement
public class MongoDBApplication {
    public static void main(String[] args) {
        SpringApplication.run(MongoDBApplication.class, args);
    }
}
