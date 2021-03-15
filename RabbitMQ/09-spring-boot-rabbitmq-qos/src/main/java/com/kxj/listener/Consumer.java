package com.kxj.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author kxj
 * @date 2021/3/15 23:14
 * @desc
 */

@Component
public class Consumer {

    @RabbitListener(queues = "ack-topic-queue")
    public void consumer(Message message, Channel channel) throws IOException, InterruptedException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            System.out.println(Thread.currentThread().getId() + ":" + new String(message.getBody()));
            channel.basicAck(deliveryTag, false);
            Thread.sleep(1000);
        } catch (Exception e) {
            channel.basicNack(deliveryTag, false, true);
        }
    }

    @RabbitListener(queues = "ack-topic-queue", containerFactory = "consumerListenerContainer")
    public void consumer2(Message message, Channel channel) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            System.out.println(Thread.currentThread().getId() + ":" + new String(message.getBody()));
            channel.basicAck(deliveryTag, false);
            Thread.sleep(1000);
        } catch (Exception e) {
            channel.basicNack(deliveryTag, false, true);
        }
    }
}
