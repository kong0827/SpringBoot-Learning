package com.kxj.service;

import com.kxj.config.StateMachineConfigBuilder;
import com.kxj.enums.Events;
import com.kxj.enums.States;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class StateMachineStart implements CommandLineRunner {

//    @Resource
//    private StateMachine<States, Events> stateMachine;

    @Resource
    private StateMachineConfigBuilder stateMachineConfigBuilder;
    @Resource
    private BeanFactory beanFactory;

    @Override
    public void run(String... args) throws Exception {
//        Message message = MessageBuilder.withPayload(Events.START_DEAL).setHeader("content", "123").build();
//        stateMachine.sendEvent(message);
//
//        boolean isError = stateMachine.hasStateMachineError();
//        if (isError) {
//            Object exception = stateMachine.getExtendedState().getVariables().get("exception");
//            System.out.println(exception);
//        }
//        stateMachine.sendEvent(Events.FINISH);


        StateMachine<States, Events> stateMachine = stateMachineConfigBuilder.build(beanFactory, States.TO_DEAL);
        stateMachine.start();
        Message<Events> message = MessageBuilder.withPayload(Events.FINISH)
                .setHeader("content", "123")
                .setHeader("action", Events.START_DEAL)
                .build();
        boolean a = stateMachine.sendEvent(message);

        log.info("event：{}", a);
//
//        Message<Events> message1 = MessageBuilder.withPayload(Events.FINISH)
//                .setHeader("content", "123")
//                .build();
//        boolean b = stateMachine.sendEvent(message1);
//        log.error("event1：{}", b);
    }
}