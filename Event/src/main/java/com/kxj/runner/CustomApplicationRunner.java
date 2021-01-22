package com.kxj.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author xiangjin.kong
 * @date 2021/1/22 14:23
 */
@Component
public class CustomApplicationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("ApplicationRunner 项目启动时启动------");
    }
}
