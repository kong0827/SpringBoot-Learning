package com.kxj.task;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 异步
 * @author xiangjin.kong
 * @date 2020/9/9 14:17
 */
@Component
public class AsyncTaskServiceImpl extends TaskService {

    @Async(value = "pool")
    @Override
    public void oneTask() throws InterruptedException {
        super.oneTask();
    }

    @Async(value = "pool")
    @Override
    public void twoTask() throws InterruptedException {
        super.twoTask();
    }

    @Async
    @Override
    public void threeTask() throws InterruptedException {
        super.threeTask();
    }
}
