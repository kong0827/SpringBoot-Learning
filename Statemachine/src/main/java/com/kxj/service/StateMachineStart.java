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
        Message message = MessageBuilder.withPayload(Events.E1).build();
        stateMachine.sendEvent(message);
        stateMachine.sendEvent(Events.E2);
        stateMachine.sendEvent(Events.E3);
    }
}