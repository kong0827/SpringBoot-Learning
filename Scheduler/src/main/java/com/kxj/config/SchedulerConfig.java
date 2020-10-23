package com.kxj.config;

import com.kxj.dao.SpringScheduledCronRepository;
import com.kxj.entity.SpringScheduledCron;
import com.kxj.utils.SpringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.util.List;

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

    @Autowired
    SpringScheduledCronRepository springScheduledCronRepository;

    @Autowired
    ApplicationContext context;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskScheduler executor = getThreadPoolTaskScheduler();

        // 设置线程池
        taskRegistrar.setScheduler(executor);

        List<SpringScheduledCron> springScheduledCrons = springScheduledCronRepository.findAll();
        for (SpringScheduledCron springScheduledCron : springScheduledCrons) {
            Class<?> clazz = null;
            Object task = null;
            try {
                clazz = Class.forName(springScheduledCron.getCronKey());
                task = context.getBean(clazz);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            taskRegistrar.addTriggerTask(((Runnable) task), triggerContext -> {
                String cronExpression = springScheduledCronRepository.findByCronKey(springScheduledCron.getCronKey()).getCronExpression();
                return new CronTrigger(cronExpression).nextExecutionTime(triggerContext);
            });
        }
    }

    private ThreadPoolTaskScheduler getThreadPoolTaskScheduler() {
        ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
        executor.setPoolSize(corePoolSize);
        executor.setThreadNamePrefix("scheduled-task-pool-");
        executor.initialize();
        return executor;
    }
}
