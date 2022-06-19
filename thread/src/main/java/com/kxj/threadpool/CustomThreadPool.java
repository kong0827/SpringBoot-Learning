package com.kxj.threadpool;

import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author kxj
 * @date 2022/5/3 21:40
 * @desc
 */
@Component
public class CustomThreadPool {

    // 处理器数量
    private final static int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    private final static ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(AVAILABLE_PROCESSORS, AVAILABLE_PROCESSORS * 2,
            1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(5), new ThreadPoolExecutor.CallerRunsPolicy());

    public void testAsync() throws InterruptedException {
        long start = System.currentTimeMillis();
        POOL_EXECUTOR.execute(() -> {
            System.out.println("-----A事件----");
        });
        System.out.println("----B事件-------");

        // 同步等待线程A运行结束
        System.out.println(System.currentTimeMillis() - start);

        // 挂起当前线程
        // join方法是Thread类中的一个方法，该方法的定义是等待该线程执行直到终止。其实就说join方法将挂起调用线程的执行，直到被调用的对象完成它的执行
        // 简单来说就是父线程等待子线程结束后才能继续运行。
        Thread.currentThread().join();
    }

}
