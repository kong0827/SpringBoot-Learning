package com.kxj.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @author xiangjin.kong
 * @date 2021/2/22 16:09
 * @desc 当消息不能被正确路由到某个queue时，会回调如下方法
 *
 * ReturnCallback：
 *      成功到达exchange，但routing不到任何queue时会调用。
 *      可以看到这里能直接拿到message，exchange，routingKey信息
 */
@Slf4j
@Component
public class ReturnCallback implements RabbitTemplate.ReturnCallback {
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        String msg = new String(message.getBody());
        log.error(String.format("消息{%s}不能被正确路由，routingKey为{%s}", msg, routingKey));
    }
}
