package com.kxj.receive;

import com.kxj.config.RabbitMqConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author xiangjin.kong
 * @date 2021/2/20 16:35
 */
@Service
@Slf4j
public class MQReceiver {
    /**
     * 监听的queue
     */
    @RabbitListener(queues = RabbitMqConfig.QUEUE)
    public void receive(String msg) {
        log.info("com.kxj.receive msg: " + msg);
    }
}
