package com.kxj.task;

import org.springframework.stereotype.Component;

/**
 * 同步
 * @author xiangjin.kong
 * @date 2020/9/9 14:15
 */
@Component
public class SyncTaskServiceImpl extends TaskService {
    @Override
    public void oneTask() throws InterruptedException {
        super.oneTask();
    }

    @Override
    public void twoTask() throws InterruptedException {
        super.twoTask();
    }

    @Override
    public void threeTask() throws InterruptedException {
        super.threeTask();
    }
}
