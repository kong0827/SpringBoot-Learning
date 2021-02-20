package com.kxj;

import net.bytebuddy.asm.Advice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kxj
 * @date 2020/4/23 21:36
 * @desc
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RabbitMQTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private AmqpAdmin amqpAdmin;

    /**
     * 点对点 测试发送消息
     */
    @Test
    public void testSend() {
        String str = "Hello";
        Map<String, String> map = new HashMap();
        map.put("1", "tom");
        map.put("2", "lucy");
        rabbitTemplate.convertAndSend("exchange-direct", "queues.news", str);
        rabbitTemplate.convertAndSend("exchange-direct", "queues.news", map);
    }

    /**
     * 点对点 测试接收消息
     */
    @Test
    public void testReceive() {
        Object o = rabbitTemplate.receiveAndConvert("queues.news");
        System.out.println(o);
    }

    /**
     * 广播
     */
    @Test
    public void testSend2() {
        Map<String, String> map = new HashMap();
        map.put("1", "tom");
        map.put("2", "lucy");
        rabbitTemplate.convertAndSend("exchange-fanout", "", map);
    }

    @Test
    public void testSend3() {
        Map<String, String> map = new HashMap();
        map.put("1", "tom");
        map.put("2", "lucy");
        rabbitTemplate.convertAndSend("exchange-topic", "queue", map);
    }

    /**
     * 创建exchange
     */
    @Test
    public void testCreateExchange() {
        Exchange exchange = new DirectExchange("direct-exchange-02");
        amqpAdmin.declareExchange(exchange);
}

    /**
     * 创建队列
     */
    @Test
    public void testCreateQueue() {
        Queue queue = new Queue("queue-02");
        amqpAdmin.declareQueue(queue);
    }

    /**
     * 创建交换器和队列的绑定规则
     */
    @Test
    public void testCreateBinding() {
        Binding binding = new Binding("queue-02", Binding.DestinationType.QUEUE, "direct-exchange-02", "queue-02", null);
        amqpAdmin.declareBinding(binding);
    }


}
