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
public class ErrorAction implements Action<States, Events> {
    @Override
    public void execute(StateContext<States, Events> context) {
        Exception exception = context.getException();
        if (exception != null) {
            log.info("异常");
            context.getStateMachine().setStateMachineError(exception);
            context.getExtendedState().getVariables().put("exception", exception);
        }
    }
}
