package com.kxj.task;

import com.kxj.enums.StatusEnum;
import com.kxj.dao.SpringScheduledCronRepository;
import com.kxj.entity.SpringScheduledCron;
import com.kxj.utils.SpringUtils;

/**
 * @author xiangjin.kong
 * @date 2020/10/23 17:28
 */
public interface ScheduledOfTask extends Runnable {
    /**
     * 定时任务方法
     */
    void execute();
    /**
     * 实现控制定时任务启用或禁用的功能
     */
    @Override
    default void run() {
        SpringScheduledCronRepository repository = SpringUtils.getBean(SpringScheduledCronRepository.class);
        SpringScheduledCron scheduledCron = repository.findByCronKey(this.getClass().getName());
        if (StatusEnum.DISABLED.getCode().equals(scheduledCron.getStatus())) {
            // 任务是禁用状态
            return;
        }
        execute();
    }
}
