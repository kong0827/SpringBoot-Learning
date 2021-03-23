package com.kxj.listerner;

import com.kxj.config.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.stereotype.Component;

/**
 * @author kxj
 * @date 2021/3/20 22:26
 * @desc
 */
@Component
@org.springframework.amqp.rabbit.annotation.RabbitListener(queues = RabbitConfig.COMMON_QUEUE)
public class RabbitListener {

    @RabbitHandler
    public void listener(String message) {
        System.out.println(message);
        int i = 1 / 0;
    }
}
