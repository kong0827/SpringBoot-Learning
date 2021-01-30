package com.kxj.listener;

import org.springframework.boot.context.event.*;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author xiangjin.kong
 * @date 2021/1/15 16:43
 */
@Component
public class ApplicationEventListener {

    @EventListener(String.class)
    public void listener(String message) {
        System.out.println("事件监听, 消息为：" + message);
    }

    @EventListener
    public void handleEvent(Object event) {
        if (event instanceof ApplicationFailedEvent) {
            System.out.println("注解-项目启动失败事件监听");
        }

        if (event instanceof ApplicationStartedEvent) {
            System.out.println("注解-项目启动时间监听");
        }

        if (event instanceof ApplicationPreparedEvent) {
            System.out.println("注解-上下文context准备时触发");
        }

        if (event instanceof ApplicationReadyEvent) {
            System.out.println("注解-上下文已经准备完毕的时候触发");
        }

        if (event instanceof SpringApplicationEvent) {
            System.out.println("注解-获取SpringApplication");
        }

        if (event instanceof ApplicationEnvironmentPreparedEvent) {
            System.out.println("注解-环境事先准备");
        }
    }
}
