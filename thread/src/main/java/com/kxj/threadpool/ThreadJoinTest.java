package com.kxj.threadpool;

/**
 * @author kxj
 * @date 2022/5/3 22:04
 * @desc
 */
public class ThreadJoinTest {

    public void joinTest() throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            System.out.println("thread1");
        });
        Thread thread2 = new Thread(() -> {
            System.out.println("thread2");
        });
        thread1.start();
        thread2.start();

        // join方法是Thread类中的一个方法，该方法的定义是等待该线程执行直到终止。其实就说join方法将挂起调用线程的执行，直到被调用的对象完成它的执行
        // 简单来说就是父线程等待子线程结束后才能继续运行。
        thread1.join();
        thread2.join();
        System.out.println("main");
    }
}
