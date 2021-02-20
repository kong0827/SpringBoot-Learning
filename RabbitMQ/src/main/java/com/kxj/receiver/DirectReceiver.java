package com.kxj.receiver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author xiangjin.kong
 * @date 2021/2/19 15:06
 */
@Slf4j
@Component
@RabbitListener(queues = {"directQueue"})
public class DirectReceiver {

    @RabbitHandler
    public void receiverMsg(String msg) {
        log.info("class:{},message:{}", "DirectReceiver", msg);
    }
}
