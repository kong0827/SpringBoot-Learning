package com.kxj.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kxj
 * @date 2021/3/9 23:50
 * @desc
 */
@Configuration
public class RabbitMQConfig {


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         @Qualifier("confirmCallback") ConfirmCallback confirmCallback,
                                         @Qualifier("returnCallback") ReturnCallback returnCallback) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        // 设置交换机处理消息的模式 要想使 returnCallback 生效，必须设置为true
        rabbitTemplate.setMandatory(true);
        return rabbitTemplate;
    }

    @Bean
    public Exchange exchange() {
        return ExchangeBuilder.topicExchange("exchange-7").durable(true).build();
    }

    @Bean
    public Queue queue() {
        return QueueBuilder.durable("queue-7").build();
    }

    @Bean
    public Binding binding(@Qualifier("exchange") Exchange exchange, @Qualifier("queue") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with("test").noargs();
    }

}
