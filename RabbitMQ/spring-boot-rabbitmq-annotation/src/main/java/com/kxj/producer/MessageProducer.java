package com.kxj.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author xiangjin.kong
 * @date 2021/2/22 14:45
 */
@Slf4j
@Component
public class MessageProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${log.exchange}")
    private String exchange;

    @Value("${log.info.binding-key}")
    private String routingKey;

    public void producer() {
        String message = "this is info message";
        amqpTemplate.convertAndSend(exchange, routingKey, message);
    }
}
