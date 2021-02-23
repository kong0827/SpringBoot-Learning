package com.kxj.receive;


import com.kxj.RabbitMqConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author xiangjin.kong
 * @date 2021/2/20 16:35
 */
@Slf4j
@Service
public class MQReceiver {
    /**
     * 监听的queue
     */
    @RabbitListener(queues = RabbitMqConfig.TOPIC_QUEUE_1)
    public void receiveQueue1(String msg) {
        log.info("receive topic1: " + msg);
    }

    @RabbitListener(queues = RabbitMqConfig.TOPIC_QUEUE_2)
    public void receiveQueue(String msg) {
        log.info("receive topic2: " + msg);
    }
}
