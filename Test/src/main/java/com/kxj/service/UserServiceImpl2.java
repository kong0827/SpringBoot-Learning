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
public class UserServiceImpl2 implements UserService2 {

    @Autowired
    UserMapper userMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addUser()  {
        System.out.println("子事务名称" + TransactionSynchronizationManager.getCurrentTransactionName());
        System.out.println("子线程" + Thread.currentThread().getId());
        User user = new User();
        user.setName("b");
        userMapper.insert(user);
        try {
            System.out.println(1 / 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
