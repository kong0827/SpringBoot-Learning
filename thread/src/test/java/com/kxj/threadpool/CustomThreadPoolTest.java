package com.kxj.threadpool;

import com.kxj.ThreadApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author kxj
 * @date 2022/5/3 21:57
 * @desc
 */
@SpringBootTest(classes = ThreadApplication.class)
@RunWith(SpringRunner.class)
public class CustomThreadPoolTest {

    @Resource
    private CustomThreadPool customThreadPool;

    @Test
    public void joinTest() throws InterruptedException {
        ThreadJoinTest threadJoinTest = new ThreadJoinTest();
        // 经过过次测试，可能出现会出现thread2在thread1之前，但是main总是会出现在最后。
        threadJoinTest.joinTest();
    }

    @Test
    public void testAsync() throws InterruptedException {
        customThreadPool.testAsync();
    }


}
