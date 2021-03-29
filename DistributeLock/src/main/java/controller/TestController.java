package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author kxj
 * @date 2021/3/28 0:27
 * @desc
 */
@RestController
public class TestController {

    @Autowired
    StringRedisTemplate redisTemplate;

    /**
     * 模拟商品减库存操作
     * @param productCode
     */
    @PutMapping("reduce/{product-code}")
    public void reduce(@PathVariable(value = "product-code") String productCode) {
        String lockKey = "lock:" + productCode;
        try {
            Boolean isSuccess = redisTemplate.opsForValue().setIfAbsent(lockKey, productCode, 10, TimeUnit.MILLISECONDS);

            if (isSuccess) {
                // 获取锁成功 执行减库存操作
                Integer count = Integer.parseInt(redisTemplate.opsForValue().get(productCode));
                if (count > 0) {
                    redisTemplate.opsForValue().increment(productCode);
                }
            }
        } finally {
            // 释放锁
            redisTemplate.delete(lockKey);
        }
    }
}
