package com.kxj.controller;

import com.kxj.config.RabbitConfig;
import com.kxj.config.TopicRabbitConfig;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author kxj
 * @date 2023/8/2 23:32
 * @desc
 */
@RestController
public class RabbitMqController {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RabbitConfig rabbitConfig;
    @Autowired
    private TopicRabbitConfig topicRabbitConfig;
    @Autowired
    @Qualifier("topicRabbitTemplate")
    private RabbitTemplate topicRabbitTemplate;

    @GetMapping("test")
    public void test() {
        rabbitTemplate.convertAndSend(rabbitConfig.getExchangeName(), rabbitConfig.getRoutingKey(), "测试");
    }

    @GetMapping("test2")
    public void test2() {
        String msgId = UUID.randomUUID().toString();
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(msgId);
        topicRabbitTemplate.convertAndSend(topicRabbitConfig.getExchangeName(), topicRabbitConfig.getRoutingKey(), "测试", correlationData);
    }
}
