package com.kxj.websocketlearning.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author kxj
 * @date 2023/9/3 23:04
 * @desc
 * 首先，我们启用 WebSocket 功能。为此，我们需要向应用程序添加配置并使用@EnableWebSocketMessageBroker注释此类。
 *
 * 顾名思义，它支持 WebSocket 消息处理，并由消息代理支持：
 *
 */
@Component
public class WebSocketConfig {

    /**
     * ServerEndpointExporter 作用
     *
     * 这个Bean会自动注册使用@ServerEndpoint注解声明的websocket endpoint
     *
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}