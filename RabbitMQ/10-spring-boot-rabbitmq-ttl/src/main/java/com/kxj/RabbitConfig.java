package com.kxj;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kxj
 * @date 2021/3/8 23:26
 * @desc
 */
@Configuration
public class RabbitConfig {

    public static final String TTL_QUEUE = "ttl-queue";
    public static final String TTL_EXCHANGE = "ttl-exchange";

    /**
     * 1、定义交换机
     * 2、定义队列
     * 3、定义队列和交换机的绑定
     */

    @Bean
    public Exchange exchange() {
        return ExchangeBuilder.topicExchange(TTL_EXCHANGE).build();
    }

    /**
     * 设置队列的过期时间
     * @return
     */
    @Bean
    public Queue queue() {
        Map<String, Object> map = new HashMap<>(10);
        // 队列中的消息未被消费则30秒后过期
        map.put("x-message-ttl", 30000);
        return QueueBuilder.durable(TTL_QUEUE).withArguments(map).build();
    }

    @Bean
    public Binding binding(@Qualifier(value = "exchange") Exchange exchange, @Qualifier(value = "queue") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with("#.user").noargs();
    }
}
