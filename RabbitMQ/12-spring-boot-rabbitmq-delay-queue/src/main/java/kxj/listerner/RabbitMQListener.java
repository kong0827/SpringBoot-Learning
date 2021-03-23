package kxj.listerner;

import kxj.config.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author kxj
 * @date 2021/3/20 22:26
 * @desc
 */
@Component
@RabbitListener(queues = RabbitConfig.DLX_QUEUE)
public class RabbitMQListener {

    @RabbitHandler
    public void listener(String message) {
        System.out.println("延迟队列：" + message);
    }
}
