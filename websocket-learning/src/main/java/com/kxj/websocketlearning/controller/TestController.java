package com.kxj.websocketlearning.controller;

import com.kxj.websocketlearning.config.WebSocket;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kxj
 * @date 2023/9/3 23:36
 * @desc
 */
@RestController
public class TestController {

    private WebSocket webSocket;

    public TestController(WebSocket webSocket) {
        this.webSocket = webSocket;
    }

    @GetMapping("point-send")
    public void testSendToUser(String name, String message) {
        webSocket.AppointSending(name, message);
    }

    @GetMapping("group-send")
    public void testSendToGroup(String message) {
        webSocket.GroupSending(message);
    }

}
