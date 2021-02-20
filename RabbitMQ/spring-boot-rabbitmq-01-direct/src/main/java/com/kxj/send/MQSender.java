package com.kxj.send;

import com.kxj.config.RabbitMqConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
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
        log.info("com.kxj.send msg: " + msg);
        amqpTemplate.convertAndSend(RabbitMqConfig.QUEUE, msg);
    }
}
