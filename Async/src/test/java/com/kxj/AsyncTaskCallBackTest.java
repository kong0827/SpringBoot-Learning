package com.kxj;

import com.kxj.task.AsyncCallBackTaskServiceImpl;
import com.kxj.task.TaskService;
import org.apache.commons.lang.WordUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Future;

/**
 * @author xiangjin.kong
 * @date 2020/9/9 14:26
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class AsyncTaskCallBackTest {

    @Autowired
    @Qualifier("asyncCallBackTaskServiceImpl")
    private AsyncCallBackTaskServiceImpl taskService;

    @Test
    public void test() throws InterruptedException {

        LocalDateTime startTime = LocalDateTime.now();

        Future<String> oneTaskCallback = taskService.oneTaskCallback();
        Future<String> twoTaskCallback = taskService.twoTaskCallback();
        Future<String> threeTaskCallback = taskService.threeTaskCallback();

        // 三个任务循环完成，退出等待
        while (!oneTaskCallback.isDone() || !twoTaskCallback.isDone() || !threeTaskCallback.isDone()) {
            Thread.sleep(1000);
        }

        System.out.println(Duration.between(startTime, LocalDateTime.now()).toMillis());
    }


}
