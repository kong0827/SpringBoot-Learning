package com.kxj.task;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 * 异步回调
 * @author xiangjin.kong
 * @date 2020/9/9 14:22
 */
@Component
public class AsyncCallBackTaskServiceImpl extends TaskService {

    @Async
    public Future<String> oneTaskCallback() throws InterruptedException {
        super.oneTask();
        return new AsyncResult<>("任务一完成");
    }

    public Future<String> twoTaskCallback() throws InterruptedException {
        super.twoTask();
        return new AsyncResult<>("任务二完成");
    }

    public Future<String> threeTaskCallback() throws InterruptedException {
        super.threeTask();
        return new AsyncResult<>("任务三完成");
    }
}
