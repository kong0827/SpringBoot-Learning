package com.kxj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author xiangjin.kong
 * @date 2021/1/29 13:49
 */
@Service
public class MoneyTransferService {

    @Autowired
    private RedisScript<Boolean> script;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void transfer(String fromAccount, String toAccount, int amount) {
        redisTemplate.execute(script, Arrays.asList(fromAccount, toAccount), String.valueOf(amount));
    }
}
