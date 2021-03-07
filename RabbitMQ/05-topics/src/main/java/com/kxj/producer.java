package com.kxj;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author kxj
 * @date 2021/3/7 23:52
 * @desc
 */
public class producer {

    private final static String TOPIC_QUEUE_1 = "topic-queue-1";
    private final static String TOPIC_QUEUE_2 = "topic-queue-2";
    private static final String TOPIC_EXCHANGE = "topic-exchange";

    public static void main(String[] args) {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("47.102.218.26");
        connectionFactory.setPort(5672);
        try (Connection connection = connectionFactory.newConnection()) {
            Channel channel = connection.createChannel();

            channel.exchangeDeclare(TOPIC_EXCHANGE, BuiltinExchangeType.TOPIC, true, false, false, null);

            channel.queueDeclare(TOPIC_QUEUE_1, true, false, false, null);
            channel.queueDeclare(TOPIC_QUEUE_2, true, false, false, null);

            // 绑定交换机和队列，根据路由
            channel.queueBind(TOPIC_QUEUE_1, TOPIC_EXCHANGE, "*.orange.*");
            channel.queueBind(TOPIC_QUEUE_1, TOPIC_EXCHANGE, "lazy.*");
            channel.queueBind(TOPIC_QUEUE_2, TOPIC_EXCHANGE, "*.*.rabbit");
            channel.queueBind(TOPIC_QUEUE_2, TOPIC_EXCHANGE, "lazy.#");

            // 发送消息
            channel.basicPublish(TOPIC_EXCHANGE, "fruit.orange.weight", null, "*.orange.*".getBytes());
            channel.basicPublish(TOPIC_EXCHANGE, "user.info.rabbit", null, "*.*.rabbit".getBytes());
            channel.basicPublish(TOPIC_EXCHANGE, "lazy.rabbit", null, "lazy.# / lazy.*".getBytes());
            channel.close();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
