package com.kxj.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @author xiangjin.kong
 * @date 2021/2/22 16:09
 * @desc 用来判断是否被ack.  ack true for ack, false for nack
 *
 * ConfirmCallback：
 *      每一条发到rabbitmq server的消息都会调一次confirm方法。
 *      如果消息成功到达exchange，则ack参数为true，反之为false；
 *      cause参数是错误信息；
 *      CorrelationData可以理解为context，在发送消息时传入的这个参数，此时会拿到。
 */
@Slf4j
@Component
public class ConfirmCallback implements RabbitTemplate.ConfirmCallback {
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            log.info("传递消息到交换机成功,correlationData:{}, cause:{}", JSON.toJSONString(correlationData), cause);
        } else {
            log.error("传递消息到交换机失败,correlationData:{}, cause:{}", JSON.toJSONString(correlationData), cause);
        }
    }
}
