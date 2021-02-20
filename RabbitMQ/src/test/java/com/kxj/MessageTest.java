package com.kxj;

import com.kxj.sender.DirectSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xiangjin.kong
 * @date 2021/2/19 15:08
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MessageTest {

    @Autowired
    DirectSender directSender;

    @Test
    public void directSend() {
        directSender.send(1);
    }

}
