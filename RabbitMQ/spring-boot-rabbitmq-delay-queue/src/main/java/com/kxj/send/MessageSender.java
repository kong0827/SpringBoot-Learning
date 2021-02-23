package com.kxj.send;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author xiangjin.kong
 * @date 2021/2/23 15:27
 */
@Component
public class MessageSender {

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 注意在发送的时候，必须加上一个header
     *
     * x-delay
     * @param queueName
     * @param msg
     */
    public void sendMessage(String queueName, String msg) {
        System.out.println("消息发送时间："+ LocalDateTime.now());
        rabbitTemplate.convertAndSend("test_exchange", queueName, msg, message -> {
            message.getMessageProperties().setHeader("x-delay",30000);
            return message;
        });
    }
}
