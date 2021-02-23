package com.kxj;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author xiangjin.kong
 * @date 2020/6/19 15:18
 * @desc
 */
@Configuration
@EnableWebSocketMessageBroker  //启用由消息代理支持的WebSocket消息处理
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 该registerStompEndpoints()方法注册/gs-guide-websocket端点，启用SockJS后备选项，
     * 以便在WebSocket不可用时可以使用备用传输。
     * SockJS客户端将尝试连接/gs-guide-websocket并使用最佳的可用传输方式（websocket，xhr-streaming，xhr-polling等）
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/gs-guide-websocket").withSockJS();
    }

    /**
     * configureMessageBroker实现WebSocketMessageBrokerConfigurer用于配置消息代理的默认方法。
     * 它首先通过调用enableSimpleBroker()来启用一个简单的基于内存的消息代理，以将问候消息在前缀为的目的地携带回客户端/topic
     * 它还/app为绑定到的方法指定的消息的前缀@MessageMapping。此前缀将用于定义所有消息映射。
     * 例如，/app/hello是GreetingController.greeting()方法映射到处理的端点。
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }
}
