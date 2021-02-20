package com.kxj.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiangjin.kong
 * @date 2021/2/19 14:29
 */
@Slf4j
@Configuration
@EnableRabbit // 开启支持RabbitMQ @EnableRabbit
public class RabbitMQConfig {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Bean
    //@Primary
    public AmqpTemplate amqpTemplate() {
        //使用jackson 消息转换器(发送对象时候才开启)
        //rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setEncoding("UTF-8");
        rabbitTemplate.setMandatory(true);
        // 开启returncallback    yml 需要配置publisher-returns: true
        rabbitTemplate.setReturnCallback(((message, replyCode, replyText, exchange, routingKey) -> {
            String correlationId = message.getMessageProperties().getCorrelationId();
            log.info("消息：{} 发送失败, 应答码：{} 原因：{} 交换机: {}  路由键: {}", correlationId, replyCode, replyText, exchange, routingKey);
        }));
        //开启消息确认  yml 需要配置   publisher-returns: true
        rabbitTemplate.setConfirmCallback(((correlationData, ack, cause) -> {
            if (ack) {
                log.info("消息发送到交换机成功,correlationId:{}", correlationData.getId());
            } else {
                log.info("消息发送到交换机失败,原因:{}", cause);
            }
        }));
        return rabbitTemplate;
    }

    /**
     * 声明直连交换机 支持持久化.
     *
     * @return the exchange
     */
    @Bean("directExchange")
    public Exchange directExchange() {
        return ExchangeBuilder.directExchange("amq.direct").durable(true).build();
    }

    @Bean("directQueue")
    public Queue directQueue() {
        return new Queue("directQueue", true, true, true);
    }

    @Bean
    public Binding directBinding(@Qualifier("directQueue") Queue queue, @Qualifier("directExchange") Exchange directExchange) {
        return BindingBuilder.bind(queue).to(directExchange).with("direct_routingKey").noargs();
    }
}
