package kxj.producer;

import kxj.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author kxj
 * @date 2021/3/20 22:10
 * @desc
 */
@Component
public class RabbitProducer {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void producer(String message) {
        rabbitTemplate.convertAndSend(RabbitConfig.COMMON_EXCHANGE, RabbitConfig.COMMON_ROUTING_KEY, message);
    }

}
