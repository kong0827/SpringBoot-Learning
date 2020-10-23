package com.kxj.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author xiangjin.kong
 * @date 2020/10/23 17:36
 */
@Component
public class DynamicPrintTask2 implements ScheduledOfTask {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private int i;

    @Override
    public void execute() {
        logger.info("thread id:{},DynamicPrintTask execute times:{}", Thread.currentThread().getId(), ++i);
    }

}
