package com.kxj.config;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author xiangjin.kong
 * @date 2020/9/9 11:01
 */
@Component
public class ScheduleConfig {

    @Scheduled(cron = "1/5 * * * * ?")
    public void schedule() {
        System.out.println("schedule run ......." + LocalDateTime.now());
    }
}
