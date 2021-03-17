package com.kxj;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @author kxj
 * @date 2021/3/8 23:45
 * @desc
 */
@Component
public class RabbitProducer {

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 队列整体过期
     */
    public void producer() {
        rabbitTemplate.convertAndSend(RabbitConfig.TTL_EXCHANGE, "kxj.user", "hello rabbitmq");
    }

    /**
     * 消息单独过期
     */
    public void producer2() {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setExpiration("10000");
        Message message = new Message("hello rabbitmq".getBytes(StandardCharsets.UTF_8), messageProperties);
        rabbitTemplate.convertAndSend(RabbitConfig.TTL_EXCHANGE, "kxj.user", message);
    }

    /**
     * 消息单独过期
     */
    public void producer3() {
        MessagePostProcessor messagePostProcessor = message -> {
             message.getMessageProperties().setExpiration("20000");
            return message;
        };
        rabbitTemplate.convertAndSend(RabbitConfig.TTL_EXCHANGE, "kxj.user", "hello rabbitmq", messagePostProcessor);
    }
}
