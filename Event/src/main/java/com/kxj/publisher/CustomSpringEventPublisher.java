package com.kxj.publisher;

import com.kxj.listener.CustomSpringEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xiangjin.kong
 * @date 2021/1/15 17:04
 * @desc 事件发布者
 */
@Component
public class CustomSpringEventPublisher {
    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Transactional(rollbackFor = Exception.class)
    public void publishCustomEvent(String message) {
        CustomSpringEvent customSpringEvent = new CustomSpringEvent(this, message);
        applicationEventPublisher.publishEvent(customSpringEvent);
        int i = 1 / 0;
    }
}
