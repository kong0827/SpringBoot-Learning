package com.kxj.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author xiangjin.kong
 * @date 2021/2/22 16:32
 */
@Slf4j
@Component
public class LogConsumer {

    /**
     * 接受info级别的日志
     */
    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "${log.info.queue}", durable = "true"),
            exchange = @Exchange(value = "${log.exchange}", type = ExchangeTypes.TOPIC),
            key = "${log.info.binding-key}"))
    public void infoLog(Message message, Channel channel) throws IOException, InterruptedException {
        String msg = new String(message.getBody());
        System.out.println(String.format("infoQueue接收到的消息为：{%s}", msg));
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }
    }
}
