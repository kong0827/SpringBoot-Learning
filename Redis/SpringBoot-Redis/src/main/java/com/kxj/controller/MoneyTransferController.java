package com.kxj.controller;

import com.kxj.service.MoneyTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiangjin.kong
 * @date 2021/1/29 13:59
 */
@RestController
public class MoneyTransferController {

    @Autowired
    MoneyTransferService moneyTransferService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @PostMapping
    public void transfer() {
        this.redisTemplate.opsForHash().put("account", "a", "100");
        this.redisTemplate.opsForHash().put("account", "b",  "20");

        moneyTransferService.transfer("a", "b", 20);

        System.out.println(
                this.redisTemplate.opsForHash().get("account", "a")
        );
        System.out.println(
                this.redisTemplate.opsForHash().get("account", "b")
        );
    }
}
