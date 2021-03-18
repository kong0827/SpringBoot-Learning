package com.kxj.producer;

import com.kxj.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author kxj
 * @date 2021/3/8 23:45
 * @desc
 */
@Component
public class RabbitProducer {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void topicProducer() {
        rabbitTemplate.convertAndSend(RabbitMQConfig.AKC_TOPIC_EXCHANGE, "topic-ack.test", "hello topic");
    }

    public void directProducer() {
        rabbitTemplate.convertAndSend(RabbitMQConfig.ACK_DIRECT_EXCHANGE, RabbitMQConfig.ACK_DIRECT, "hello direct");
    }
}
