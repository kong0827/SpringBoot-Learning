package com.kxj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;

/**
 * @author xiangjin.kong
 * @date 2020/10/22 14:12
 */
@Configuration
public class SchedulerConfig implements SchedulingConfigurer {
    /*
     此处成员变量应该使用@Value从配置中读取
     */
    private int corePoolSize = 10;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
        executor.setPoolSize(corePoolSize);
        executor.setThreadNamePrefix("scheduled-task-pool-");
        executor.initialize();

        taskRegistrar.setScheduler(executor);

    }
}
