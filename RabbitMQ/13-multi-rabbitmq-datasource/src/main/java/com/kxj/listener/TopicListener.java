package com.kxj.listener;

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
 * @author kxj
 * @date 2023/9/4 23:40
 * @desc
 */
@Slf4j
@Component
public class TopicListener {

    @RabbitListener(containerFactory = "topicContainerFactory",
            bindings = @QueueBinding(value = @Queue(value = "multi-datasource-topic-queue-1", durable = "true"),
            exchange = @Exchange(value = "multi-datasource-topic-exchange-1", type = ExchangeTypes.TOPIC),
            key = "multi-routing-key-topic-1"))
    public void consumer(Message message, Channel channel) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("接受到的数据：{}",new String(message.getBody()));
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            channel.basicNack(deliveryTag, false, true);
        }
    }

}
