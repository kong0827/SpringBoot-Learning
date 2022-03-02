package com.kxj.config;

import com.kxj.action.ErrorAction;
import com.kxj.action.FinishAction;
import com.kxj.action.StartDealAction;
import com.kxj.enums.Events;
import com.kxj.enums.States;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import javax.annotation.Resource;
import java.util.EnumSet;

@Slf4j
@Configuration
@EnableStateMachine
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {


    @Resource
    private StartDealAction startDealAction;
    @Resource
    private FinishAction finishAction;
    @Resource
    private ErrorAction errorAction;


    /**
     * 状态机配置,自动启动,添加监听器
     *
     * @param config
     * @throws Exception
     */
    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
        config
                .withConfiguration()
                .autoStartup(true)
                .listener(listener());
    }

    /**
     * 状态初始化
     *
     * @param states
     * @throws Exception
     */
    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
        states
                .withStates()
                .initial(States.TO_DEAL)
                .states(EnumSet.allOf(States.class));
    }

    /**
     * 状态迁移
     *
     * @param transitions
     * @throws Exception
     */
    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions
                .withExternal()
                .and()
                .withExternal()
                .source(States.TO_DEAL).target(States.DEALING).event(Events.START_DEAL).action(startDealAction, errorAction)
                .and()
                .withExternal()
                .source(States.DEALING).target(States.COMPLETED).event(Events.FINISH).action(finishAction, errorAction);
    }

    /**
     * 装态监听器
     *
     * @return
     */
    @Bean
    public StateMachineListener<States, Events> listener() {
        return new StateMachineListenerAdapter<States, Events>() {
            @Override
            public void stateChanged(State<States, Events> from, State<States, Events> to) {
                String fromId = from != null ? String.valueOf(from.getId()) : "";
                log.info(fromId + " State change to " + to.getId());
            }

            @Override
            public void stateMachineError(StateMachine<States, Events> stateMachine, Exception exception) {
                log.info("异常.....");
            }
        };
    }
}
