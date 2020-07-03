package com.kxj.dao.master;

import com.kxj.dao.salve.SalveUserDao;
import com.kxj.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xiangjin.kong
 * @date 2020/7/3 11:06
 * @desc
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DataSourceTest {

    @Autowired
    MasterUserDao masterUserDao;

    @Autowired
    SalveUserDao salveUserDao;

    @Test
    @Transactional(value = "transactionManagerPrimary", rollbackFor = Exception.class)
    public void test() {
        for (User user : masterUserDao.findAll()) {
            System.out.println(user);
        }
        System.out.println("-----------------------");
        for (User user : salveUserDao.findAll()) {
            System.out.println(user);
        }
    }
}
