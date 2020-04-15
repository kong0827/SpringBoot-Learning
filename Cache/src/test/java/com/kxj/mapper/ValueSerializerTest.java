package com.kxj.mapper;

import com.kxj.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author kxj
 * @date 2020/4/15 21:38
 * @desc
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ValueSerializerTest {

    @Autowired
    @Qualifier("valueRedisTemplate")
    public RedisTemplate<Object, Object> redisTemplate;

    @Test
    public void test() {
//        User user = new User(1, "张三丰", "123qwe");
        User user = new User(2, "张三丰", "123");
        redisTemplate.opsForValue().set(user.getId(), user);
    }

    @Test
    public void testValueIsString() {
        redisTemplate.opsForValue().set("string", "笑傲江湖");
    }

    @Test
    public void getValue() {
        User user = (User) redisTemplate.opsForValue().get(2);
        System.out.println(user);
    }
}
