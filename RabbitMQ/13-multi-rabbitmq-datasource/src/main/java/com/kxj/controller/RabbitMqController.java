package com.kxj.controller;

import com.kxj.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("test")
    public void test() {
        rabbitTemplate.convertAndSend(rabbitConfig.getExchangeName(), rabbitConfig.getRoutingKey(), "测试");
    }
}
