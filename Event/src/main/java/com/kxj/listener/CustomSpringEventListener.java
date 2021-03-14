package com.kxj.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @author xiangjin.kong
 * @date 2021/1/22 11:17
 * @desc 自定义事件监听
 *       ApplicationListener是一个监听器，用来监听容器中发布的事件
 */
@Component
public class CustomSpringEventListener implements ApplicationListener<CustomSpringEvent> {


    @Override
    public void onApplicationEvent(CustomSpringEvent event) {
        System.out.println("接收到的事件：" + event.getMessage());
    }

    /**
     * @TransactionalEventListener批注，它是@EventListener的扩展，它允许将事件的侦听器绑定到事务的某个阶段。
     * 可以绑定到以下事务阶段：
     * 如果事务成功完成，则使用AFTER_COMMIT（默认值）来触发事件
     * AFTER_ROLLBACK –如果事务已回滚
     * AFTER_COMPLETION –如果事务已完成（AFTER_COMMIT和AFTER_ROLLBACK的别名）
     * BEFORE_COMMIT用于在事务提交之前立即触发事件
     *
     * @param event
     */
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleCustom(CustomSpringEvent event) {
        System.out.println("事务提交之前");
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void handleCustom1(CustomSpringEvent event) {
        System.out.println("事务回滚");
    }
}
