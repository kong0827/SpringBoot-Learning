package com.kxj.listener;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author xiangjin.kong
 * @date 2021/1/15 16:43
 */
@Component
public class AsyncApplicationEventListener {


    @Async
    @EventListener(String.class)
    public void listener(String message) throws InterruptedException {
        System.out.println("1-异步事件监听, 消息为：" + message);
    }

    /**
     * 可以不指定classes，默认监听的是方法参数中的事件
     * @param message
     * @throws InterruptedException
     */
    @Async
    @EventListener(condition = "#message.length() > 1")
    public void listener2(String message) throws InterruptedException {
        System.out.println("2-异步事件监听, 消息为：" + message);
    }

    @Async
    @EventListener(condition = "#message.length() > 10")
    public void listener3(String message) throws InterruptedException {
        System.out.println("3-异步事件监听, 消息为：" + message);
    }
}
