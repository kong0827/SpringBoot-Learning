package com.kxj.listener;

import org.springframework.context.ApplicationEvent;

/**
 * @author xiangjin.kong
 * @date 2021/1/15 16:59
 * @desc 自定义事件
 */
public class CustomSpringEvent extends ApplicationEvent {

    private String message;

    public CustomSpringEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
