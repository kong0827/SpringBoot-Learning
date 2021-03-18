package com.kxj;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author kxj
 * @date 2021/3/7 22:43
 * @desc
 */
public class producer {

    private final static String FANOUT_QUEUE_1 = "fanout-queue-1";
    private final static String FANOUT_QUEUE_2 = "fanout-queue-2";
    private static final String FANOUT_EXCHANGE = "fanout-exchange";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("47.102.218.26");
        connectionFactory.setPort(5672);
        try (Connection connection = connectionFactory.newConnection()) {
            Channel channel = connection.createChannel();

            /**
             * 声明交换机
             * exchangeDeclare(String exchange,BuiltinExchangeType type,boolean durable,boolean autoDelete,boolean internal,Map<String, Object> arguments)
             * exchange：交换机名称
             * type：交换机类型
             * durable：是否持久化
             * autoDelete：是否自动删除
             * internal：设置是否是RabbitMQ内部使用，默认false。如果设置为 true ，则表示是内置的交换器，客户端程序无法直接发送消息到这个交换器中，只能通过交换器路由到交换器这种方式
             * arguments：扩展参数，用于扩展AMQP协议自制定化使用
             */
            channel.exchangeDeclare(FANOUT_EXCHANGE, BuiltinExchangeType.FANOUT, true, false, false, null);

            // 设置交换机和队列的绑定
            channel.queueDeclare(FANOUT_QUEUE_1, true, false, false, null);
            channel.queueDeclare(FANOUT_QUEUE_2, true, false, false, null);

            /**
             * queueBind(String queue, String exchange, String routingKey)
             *
             * 1、queue：队列名称
             * 2、exchange：交换机名称
             * 3、routingKey：路由 fanout 路由设置为空字符转即可
             */
            channel.queueBind(FANOUT_QUEUE_1, FANOUT_EXCHANGE, "");
            channel.queueBind(FANOUT_QUEUE_2, FANOUT_EXCHANGE, "");

            // 发送消息
            channel.basicPublish(FANOUT_EXCHANGE, "", null, "hello topic".getBytes());
            /**
             * 即使指定任意路由 也会发送到绑定的队列
             */
            channel.basicPublish(FANOUT_EXCHANGE, "", null, "hello topic".getBytes());
            channel.close();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
