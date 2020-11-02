package com.kxj.mapper;

import com.kxj.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author xiangjin.kong
 * @date 2020/11/2 14:46
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void insertTest() {
        User user = new User();
        user.setUserName("aa");
        userMapper.insert(user);
    }
}
