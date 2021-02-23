package com.kxj.sender;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiangjin.kong
 * @date 2021/2/22 16:11
 */
@Component
public class MessageSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public final Map<String, String> unAckMsgQueue = new ConcurrentHashMap<>();

    public void convertAndSend(String exchange, String routingKey, String message) {
        String msgId = UUID.randomUUID().toString();
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(msgId);
        rabbitTemplate.convertAndSend(exchange, routingKey, message, correlationData);
        unAckMsgQueue.put(msgId, message);
    }

    public String dequeueUnAckMsg(String msgId) {
        return unAckMsgQueue.get(msgId);
    }
}
