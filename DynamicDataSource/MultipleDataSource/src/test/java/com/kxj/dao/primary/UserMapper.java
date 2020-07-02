package com.kxj.dao.primary;

import com.kxj.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author xiangjin.kong
 * @date 2020/7/2 17:06
 * @desc
 */
@SpringBootTest
public class UserMapper {

    @Autowired
    MasterUserDao masterUserDao;

    @Test
    public void test() {
        List<User> users = masterUserDao.selectUsers();
        for (User user : users) {
            System.out.println(user);
        }

    }
}
