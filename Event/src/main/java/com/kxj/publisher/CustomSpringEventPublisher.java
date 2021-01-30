package com.kxj.publisher;

import com.kxj.listener.CustomSpringEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @author xiangjin.kong
 * @date 2021/1/15 17:04
 * @desc 事件发布者
 */
@Component
public class CustomSpringEventPublisher {
    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    public void publishCustomEvent(String message) {
        System.out.println("发布自定义事件");
        CustomSpringEvent customSpringEvent = new CustomSpringEvent(this, message);
        applicationEventPublisher.publishEvent(customSpringEvent);
    }
}
