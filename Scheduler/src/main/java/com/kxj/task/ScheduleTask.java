package com.kxj.task;

import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author xiangjin.kong
 * @date 2020/10/22 13:42
 */
@Component
public class ScheduleTask {

    private final Logger log = LoggerFactory.getLogger(ScheduleTask.class);

//    @Scheduled(cron = "1/2 * * * * ?")
//    public void task1() throws InterruptedException {
//        log.info("task-1, 我需要执行10秒的时间, 线程Id：{}， 线程名：{}，时间：{}",
//                Thread.currentThread().getId(), Thread.currentThread().getName(), LocalDateTime.now());
//        Thread.sleep(10000);
//        log.error("task-1 ending....., 线程Id：{}， 线程名：{}，时间：{}",
//                Thread.currentThread().getId(), Thread.currentThread().getName(), LocalDateTime.now());
//    }
//
//    @Scheduled(cron = "1/4 * * * * ?")
//    public void task2() throws InterruptedException {
//        log.info("task-2, 我需要执行两秒时间, 线程Id：{}， 线程名：{}，时间：{}",
//                Thread.currentThread().getId(), Thread.currentThread().getName(), LocalDateTime.now());
//        Thread.sleep(2000);
//        log.error("task-2 ending....., 线程Id：{}， 线程名：{}，时间：{}",
//                Thread.currentThread().getId(), Thread.currentThread().getName(), LocalDateTime.now());
//    }

}
