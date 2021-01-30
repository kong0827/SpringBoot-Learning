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

    @EventListener(String.class)
    @Async
    public void listener(String message) throws InterruptedException {
        System.out.println("异步事件监听, 消息为：" + message);
    }
}
