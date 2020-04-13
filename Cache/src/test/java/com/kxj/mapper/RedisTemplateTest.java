package com.kxj.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author kxj
 * @date 2020/4/14 0:10
 * @desc
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTemplateTest {

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void test() {
        redisTemplate.opsForValue().set("tt","ll");
    }
}
