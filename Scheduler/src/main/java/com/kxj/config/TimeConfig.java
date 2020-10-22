package com.kxj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author xiangjin.kong
 * @date 2020/9/9 10:30
 */
@Configuration
public class TimeConfig {

//    @Bean
//    public void schedule() {
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                System.out.println("TimerTask run" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
//            }
//        },1000,5000);//延时1s，之后每隔5s运行一次
//
//    }
}
