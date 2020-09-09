package com.kxj.task;

import org.apache.tomcat.jni.Local;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.sql.SQLOutput;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * @author xiangjin.kong
 * @date 2020/9/9 13:47
 */
public abstract class TaskService {


    private static Random random = new Random();

    public void oneTask() throws InterruptedException {
        System.out.println("任务一：start......");
        LocalDateTime startTime = LocalDateTime.now();
        Thread.sleep(random.nextInt(10000));
        LocalDateTime endTime = LocalDateTime.now();
        System.out.println("任务一：end.......");
        long millis = Duration.between(startTime, endTime).toMillis();
        System.out.println("任务一耗时：" + millis);
    }

    public void twoTask() throws InterruptedException {
        System.out.println("任务二：start......");
        LocalDateTime startTime = LocalDateTime.now();
        Thread.sleep(random.nextInt(10000));
        LocalDateTime endTime = LocalDateTime.now();
        System.out.println("任务二：end.......");
        long millis = Duration.between(startTime, endTime).toMillis();
        System.out.println("任务二耗时：" + millis);
    }

    public void threeTask() throws InterruptedException {
        System.out.println("任务三：start......");
        LocalDateTime startTime = LocalDateTime.now();
        Thread.sleep(random.nextInt(10000));
        LocalDateTime endTime = LocalDateTime.now();
        System.out.println("任务三：end.......");
        long millis = Duration.between(startTime, endTime).toMillis();
        System.out.println("任务三耗时：" + millis);
    }

}
