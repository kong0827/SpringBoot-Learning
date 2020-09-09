package com.kxj;

import com.kxj.task.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xiangjin.kong
 * @date 2020/9/9 13:53
 */

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class SyncTaskTest {

    @Autowired
    @Qualifier("syncTaskServiceImpl")
    TaskService taskService;

    @Test
    public void taskTest() throws InterruptedException {

        LocalDateTime startTime = LocalDateTime.now();

        taskService.oneTask();
        System.out.println();
        taskService.twoTask();
        System.out.println();
        taskService.threeTask();

        System.out.println(Duration.between(startTime, LocalDateTime.now()).toMillis());

    }
}
