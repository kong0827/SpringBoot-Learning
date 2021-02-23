package com.kxj.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author xiangjin.kong
 * @date 2021/2/23 15:29
 */
@Component
public class MessageConsumer {

    @RabbitListener(queues = "test_queue_1")
    public void receive(String msg) {
        System.out.println("消息接收时间："+ LocalDateTime.now());
        System.out.println("接收的消息：" + msg);
    }
}
