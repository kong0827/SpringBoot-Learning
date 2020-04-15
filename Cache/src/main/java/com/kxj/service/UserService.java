package com.kxj.service;

import com.kxj.entity.User;
import com.kxj.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author kxj
 * @date 2020/4/15 22:28
 * @desc
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Qualifier("redisTemplate")
    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    public User getUser(Integer id) {

        /*
         * 首先查询缓存是否存在
         */
        User user = (User) redisTemplate.opsForValue().get("user:" + id);

        /*
         * 若缓存中不存在，则查询数据库，并将查询结果设置到缓存中
         */
        if (user == null) {
            System.out.println("查询数据库....");
            user = userMapper.getUser(id);
            if (user != null) {
                redisTemplate.opsForValue().set("user:" + user.getId(), user);
            }
        }
        return user;
    }

    /**
     * 缓存击穿问题
     * 当多线程访问时候，其中一个线程已经拿到数据库数据，并设置进缓存中，另一个并发线程已经入方法内查询数据库，造成多次查询数据库，
     */
    public User getUser1(Integer id) {

        User user = (User) redisTemplate.opsForValue().get("user:" + id);

        if (user == null) {
            // 设置同步代码块
            synchronized (this) {
                // 再次重缓存中查找
                user = (User) redisTemplate.opsForValue().get("user:" + id);

                //双重检测判断缓存中是否有数据
                if (user == null) {
                    System.out.println("查询数据库......");
                    user = userMapper.getUser(id);
                    if (user != null) {
                        redisTemplate.opsForValue().set("user:" + user.getId(), user);
                    }
                }
            }
        }
        return user;
    }

}
