package com.kxj.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author kxj
 * @date 2020/4/15 21:26
 * @desc
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class KeySerializerTest {

    @Autowired
    @Qualifier("keyRedisTemplate")
    private RedisTemplate<Object, Object> redisTemplate;

    @Test
    public void testKeySerializer() {
        redisTemplate.opsForValue().set("时间", "静止");
    }
}
