package com.kxj.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author kxj
 * @date 2021/3/8 23:51
 * @desc
 */
@Component
public class TopicConsumer {

    @RabbitListener(queues = "spring-boot-queue")
    public void listener(Message message) {
        System.out.println(message);
    }
}
