package com.kxj.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author xiangjin.kong
 * @date 2022/6/21 20:51
 */
@Component
public class RedisCache {

    @Resource
    private RedisTemplate redisTemplate;


    public <T> void setCacheObject(String key, T value, final int timeout, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    public <T> T getCacheObject(final String key) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }
}
