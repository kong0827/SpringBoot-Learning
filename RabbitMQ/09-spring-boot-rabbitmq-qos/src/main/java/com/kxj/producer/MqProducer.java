package com.kxj.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author kxj
 * @date 2021/3/15 23:12
 * @desc
 */
@Component
public class MqProducer {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void topicProducer() {
        rabbitTemplate.convertAndSend("ack-topic-exchange", "topic-ack.test", "hello topic");
    }

}
