package com.kxj.action;

import com.kxj.enums.Events;
import com.kxj.enums.States;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

/**
 * @author xiangjin.kong
 * @date 2022/3/2 22:18
 */
@Slf4j
@Component
public class StartDealAction implements Action<States, Events> {
    @Override
    public void execute(StateContext<States, Events> context) {
        log.info("开始处理");
        Object content = context.getMessageHeaders().get("content");
        log.info("信息: {}", content);
        int i = 1 / 0;
    }
}
