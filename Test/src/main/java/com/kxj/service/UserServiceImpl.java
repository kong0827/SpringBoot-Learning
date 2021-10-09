package com.kxj.service;

import com.kxj.entity.User;
import com.kxj.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.time.LocalDateTime;

/**
 * @author xiangjin.kong
 * @date 2021/6/10 14:09
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    UserService2 userService2;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUser() throws InterruptedException {
        System.out.println("主线程" + Thread.currentThread().getId());
        System.out.println("主事务名称" + TransactionSynchronizationManager.getCurrentTransactionName());
        User user = new User();
        user.setName("a");
        user.setCreateTime(LocalDateTime.now());
        user.setSex(0);
        userMapper.insert(user);
        userService2.addUser();
    }

//    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addUser2() {
        User user = new User();
        user.setName("6611");

//        try {
            System.out.println(1/0);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        userMapper.insert(user);
    }
}
