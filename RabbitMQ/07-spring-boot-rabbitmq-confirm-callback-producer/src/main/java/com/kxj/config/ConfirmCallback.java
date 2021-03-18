package com.kxj.config;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @author kxj
 * @date 2021/3/9 23:58
 * @desc 消息到交换机
 */
@Component
public class ConfirmCallback implements RabbitTemplate.ConfirmCallback {
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            System.out.println("传递消息到交换机成功");
        } else {
            System.out.println("传递消息到交换机失败");
        }
    }
}
