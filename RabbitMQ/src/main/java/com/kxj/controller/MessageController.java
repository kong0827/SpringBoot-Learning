package com.kxj.controller;

import com.kxj.sender.DirectSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiangjin.kong
 * @date 2021/2/19 15:16
 */
@RestController
public class MessageController {

    @Autowired
    DirectSender directSender;

    @GetMapping("direct/{i}")
    public void directSend(@PathVariable(value = "i") int i) {
        directSender.send(i);
    }
}
