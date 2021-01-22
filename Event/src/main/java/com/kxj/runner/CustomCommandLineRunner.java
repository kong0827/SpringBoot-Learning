package com.kxj.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author xiangjin.kong
 * @date 2021/1/22 14:26
 */
@Component
public class CustomCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("CommandLineRunner 项目启动时执行----");
    }
}
