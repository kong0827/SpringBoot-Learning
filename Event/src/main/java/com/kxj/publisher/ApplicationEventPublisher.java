package com.kxj.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author xiangjin.kong
 * @date 2021/1/22 11:19
 * @desc ApplicationContext 本身可以发布各种事件
 */
@Component
public class ApplicationEventPublisher {

    @Autowired
    ApplicationContext applicationContext;

    public void publish(String message) {
        applicationContext.publishEvent(message);
    }
}
