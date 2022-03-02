package com.kxj.service;

import com.kxj.enums.Events;
import com.kxj.enums.States;
import org.springframework.boot.CommandLineRunner;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class StateMachineStart implements CommandLineRunner {

    @Resource
    private StateMachine<States, Events> stateMachine;

    @Override
    public void run(String... args) throws Exception {
        Message message = MessageBuilder.withPayload(Events.START_DEAL).setHeader("content", "123").build();
        stateMachine.sendEvent(message);

        boolean isError = stateMachine.hasStateMachineError();
        if (isError) {
            Object exception = stateMachine.getExtendedState().getVariables().get("exception");
            System.out.println(exception);
        }
//        stateMachine.sendEvent(Events.FINISH);
    }
}