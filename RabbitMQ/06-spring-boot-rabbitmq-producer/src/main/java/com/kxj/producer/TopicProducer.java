package com.kxj.producer;

import com.kxj.config.TopicConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author kxj
 * @date 2021/3/8 23:45
 * @desc
 */
@Component
public class TopicProducer {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void producer() {
        rabbitTemplate.convertAndSend(TopicConfig.SPRING_BOOT_EXCHANGE, "kxj.user", "hello rabbitmq");
    }
}
