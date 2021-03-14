package com.kxj.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kxj
 * @date 2021/3/14 23:12
 * @desc
 */
@Configuration
public class RabbitMQConfig {

    public static final String AKC_TOPIC_EXCHANGE = "ack-topic-exchange";
    public static final String ACK_TOPIC_QUEUE = "ack-topic-queue";
    public static final String ACK_TOPIC = "topic-ack.#";

    public static final String ACK_DIRECT_EXCHANGE = "ack-direct-exchange";
    public static final String ACK_DIRECT_QUEUE = "ack-direct-queue";
    public static final String ACK_DIRECT = "direct-ack";

    @Bean
    public Exchange topicExchange() {
        return ExchangeBuilder.topicExchange(AKC_TOPIC_EXCHANGE).durable(true).build();
    }

    @Bean
    public Queue topicQueue() {
        return QueueBuilder.durable(ACK_TOPIC_QUEUE).build();
    }

    @Bean
    public Binding topicBinding(@Qualifier(value = "topicExchange") Exchange exchange, @Qualifier(value = "topicQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with(ACK_TOPIC).noargs();
    }

    @Bean
    public Exchange directExchange() {
        return ExchangeBuilder.directExchange(ACK_DIRECT_EXCHANGE).durable(true).build();
    }

    @Bean
    public Queue directQueue() {
        return QueueBuilder.durable(ACK_DIRECT_QUEUE).build();
    }

    @Bean
    public Binding directBinding(@Qualifier(value = "directExchange") Exchange exchange, @Qualifier(value = "directQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with(ACK_DIRECT).noargs();
    }

}
