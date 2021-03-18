package com.kxj.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kxj
 * @date 2021/3/8 23:26
 * @desc
 */
@Configuration
public class TopicConfig {

    public static final String SPRING_BOOT_QUEUE = "spring-boot-queue";
    public static final String SPRING_BOOT_EXCHANGE = "spring-boot-exchange";

    /**
     * 1、定义交换机
     * 2、定义队列
     * 3、定义队列和交换机的绑定
     */

    @Bean
    public Exchange exchange() {
        return ExchangeBuilder.topicExchange(SPRING_BOOT_EXCHANGE).build();
    }

    @Bean
    public Queue queue() {
        return QueueBuilder.durable(SPRING_BOOT_QUEUE).build();
    }

    @Bean
    public Binding binding(@Qualifier(value = "exchange") Exchange exchange, @Qualifier(value = "queue") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with("#.user").noargs();
    }
}
