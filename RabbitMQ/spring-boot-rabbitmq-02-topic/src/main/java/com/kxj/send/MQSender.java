package com.kxj.send;


import com.kxj.RabbitMqConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiangjin.kong
 * @date 2021/2/20 16:32
 */
@Service
@Slf4j
public class MQSender {

    @Autowired
    AmqpTemplate amqpTemplate;

    public void send(Object message) {
        String msg = (String) message;
        log.info("send msg: " + msg);
        amqpTemplate.convertAndSend(RabbitMqConfig.TOPIC_EXCHANGE, RabbitMqConfig.ROUTING_KEY_1, msg + 1);
        amqpTemplate.convertAndSend(RabbitMqConfig.TOPIC_EXCHANGE, RabbitMqConfig.ROUTING_KEY_2, msg + 2);
    }
}
