package com.kxj.service;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListeners;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author kxj
 * @date 2020/4/23 22:30
 * @desc
 */

@Service
public class MQService {

    /**
     * 可以指定监听多个队列
     *
     * @param map
     */
    @RabbitListener(queues = {"queue.news", "queues.news"})
    public void receive(Map map) {
        System.out.println("收到消息：" + map);
    }

    @RabbitListener(queues = "queue")
    public void receive(Message message) {
        System.out.println(message);
    }
}
