package com.kxj.receiver;

import com.kxj.config.RabbitConfig;
import com.kxj.model.MyModel;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author xiangjin.kong
 * @date 2021/2/19 14:02
 */
@Component
@RabbitListener(queues = RabbitConfig.QUEUE_A)
public class MyReceiver {
    @RabbitHandler
    public void process(MyModel model) {
        System.out.println("接收处理队列A当中的消息： " + model.getInfo());
    }
}
