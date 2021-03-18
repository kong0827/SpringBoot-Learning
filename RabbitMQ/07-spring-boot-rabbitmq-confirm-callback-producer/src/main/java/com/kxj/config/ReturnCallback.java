package com.kxj.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @author kxj
 * @date 2021/3/10 0:12
 * @desc 交换机到队列失败时才调用
 */
@Component
public class ReturnCallback implements RabbitTemplate.ReturnCallback {
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        String msg = new String(message.getBody());
        System.out.println(String.format("消息{%s}不能被正确路由，routingKey为{%s}", msg, routingKey));
    }
}
