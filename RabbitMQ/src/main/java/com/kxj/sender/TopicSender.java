package com.kxj.sender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author xiangjin.kong
 * @date 2021/2/19 15:02
 */
@Component
@Slf4j
public class TopicSender {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(int i) {

        String content = i + ":hello!" + LocalDateTime.now();
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        log.info("class:{},message:{}", "DirectSender", content);
        this.rabbitTemplate.convertAndSend("amq.direct", "direct_routingKey", content, correlationData);
    }
}
