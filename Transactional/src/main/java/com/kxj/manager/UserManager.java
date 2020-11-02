package com.kxj.manager;

import com.kxj.entity.User;
import com.kxj.mapper.UserMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author xiangjin.kong
 * @date 2020/11/2 14:12
 */
@Component
public class UserManager {
    @Resource
    private UserMapper userMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insert(User user) {
        userMapper.insert(user);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertWithException(User user) {
        userMapper.insert(user);
        throw new RuntimeException("foo");
    }
}
