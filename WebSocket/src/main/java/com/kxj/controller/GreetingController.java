package com.kxj.controller;

import com.kxj.entity.Greeting;
import com.kxj.entity.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

/**
 * @author xiangjin.kong
 * @date 2020/6/19 15:15
 * @desc
 */

@Controller
public class GreetingController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws InterruptedException {
        Thread.sleep(1000);
        return new Greeting("Hello:" + HtmlUtils.htmlEscape(message.getName()) + "!");
    }
}
