package com.kxj.service;

import com.kxj.entity.User;
import com.kxj.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

/**
 * @author xiangjin.kong
 * @date 2021/6/10 14:09
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    @Transactional
    public void addUser() {
        User user = new User();
        user.setName(usingUUID());
        user.setCreateTime(LocalDateTime.now());
        user.setSex((int) ((Math.random() * (10 - 1)) + 1));
        userMapper.insert(user);
//        addUser2();
    }

    String usingUUID() {
        UUID randomUUID = UUID.randomUUID();
        return randomUUID.toString().replaceAll("-", "");
    }

    private void addUser2() {
        User user = new User();
        user.setName("yy");
        userMapper.insert(user);
    }
}
