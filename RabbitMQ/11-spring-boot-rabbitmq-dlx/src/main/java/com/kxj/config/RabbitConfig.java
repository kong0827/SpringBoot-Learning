package com.kxj.config;

import org.springframework.amqp.core.*;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kxj
 * @date 2021/3/20 21:46
 * @desc
 */
@Configuration
public class RabbitConfig {
    public static final String COMMON_EXCHANGE = "common_exchange";//消息接收的交换机
    public static final String COMMON_ROUTING_KEY = "common_key";//消息routing key
    public static final String COMMON_QUEUE = "common_queue"; //消息存储queue

    public static final String DLX_EXCHANGE = "dlx_exchange";//重定向交换机
    public static final String DLX_ROUTING_KEY = "dlx_key"; //重定向队列routing key
    public static final String DLX_QUEUE = "dlx_queue"; //重定向消息存储queue

    /**
     * 正常交换机的定义
     * @return
     */
    @Bean
    public Exchange commonExchange() {


        return ExchangeBuilder.directExchange(COMMON_EXCHANGE).build();
    }

    @Bean
    public Queue commonQueue() {
        Map<String, Object> args = new HashMap<>(8);

        /**
         * 消息成为死信后重定向到死信交换机
         */
        args.put("x-dead-letter-exchange", DLX_EXCHANGE);
        args.put("x-dead-letter-routing-key", DLX_ROUTING_KEY);


        // 设置超时时间
        args.put("x-message-ttl", 10000);
        // 设置队列的最大长度
        args.put("x-max-length", 10);
        return QueueBuilder.durable(COMMON_QUEUE).withArguments(args).build();
    }

    @Bean
    public Binding commonBinding() {
        return BindingBuilder.bind(commonQueue()).to(commonExchange()).with(COMMON_ROUTING_KEY).noargs();
    }

    /**
     * 定义死信交换机
     */
    @Bean
    public Exchange dlxExchange() {
        return ExchangeBuilder.directExchange(DLX_EXCHANGE).build();
    }

    /**
     * 私信队列
     * @return
     */
    @Bean
    public Queue dlxQueue() {
        return QueueBuilder.durable(DLX_QUEUE).build();
    }

    /**
     * 死信交换机和队列的绑定
     */
    @Bean
    public Binding dlxBinding() {
        return BindingBuilder.bind(dlxQueue()).to(dlxExchange()).with(DLX_ROUTING_KEY).noargs();
    }


}
