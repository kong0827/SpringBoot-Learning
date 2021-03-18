package com.kxj;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author kxj
 * @date 2021/3/7 23:31
 * @desc
 */
public class producer {

    private final static String DIRECT_QUEUE_1 = "direct-queue-1";
    private final static String DIRECT_QUEUE_2 = "direct-queue-2";
    private static final String DIRECT_EXCHANGE = "direct-exchange";

    public static void main(String[] args) {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("47.102.218.26");
        connectionFactory.setPort(5672);
        try (Connection connection = connectionFactory.newConnection()) {
            Channel channel = connection.createChannel();

            channel.exchangeDeclare(DIRECT_EXCHANGE, BuiltinExchangeType.DIRECT, true, false, false, null);

            channel.queueDeclare(DIRECT_QUEUE_1, true, false, false, null);
            channel.queueDeclare(DIRECT_QUEUE_2, true, false, false, null);

            // 绑定交换机和队列，根据路由
            channel.queueBind(DIRECT_QUEUE_1, DIRECT_EXCHANGE, "error");
            channel.queueBind(DIRECT_QUEUE_2, DIRECT_EXCHANGE, "warning");
            channel.queueBind(DIRECT_QUEUE_2, DIRECT_EXCHANGE, "info");
            channel.queueBind(DIRECT_QUEUE_2, DIRECT_EXCHANGE, "error");

            // 发送消息
            channel.basicPublish(DIRECT_EXCHANGE, "error", null, "error 级别".getBytes());
            channel.basicPublish(DIRECT_EXCHANGE, "info", null, "info 级别".getBytes());
            channel.close();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
