package com.kxj.manager;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiangjin.kong
 * @date 2021/3/23 19:23
 */
@Configuration
@ConditionalOnProperty(name = "type", prefix = "message", havingValue = "2")
public class MessageManagerImpl2 implements MessageManager {

    @Override
    public void send() {
        System.out.println("消息发送------2");
    }
}
