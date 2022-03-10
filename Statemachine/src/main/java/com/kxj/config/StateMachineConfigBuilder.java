package com.kxj.config;

import com.kxj.action.ErrorAction;
import com.kxj.action.FinishAction;
import com.kxj.action.RejectAction;
import com.kxj.action.StartDealAction;
import com.kxj.enums.Events;
import com.kxj.enums.States;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.action.Actions;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.EnumSet;

/**
 * 构建谷仓订单状态机
 *
 * @author WangShengguang
 * @version 1.0.0 2021/3/30
 */
@Slf4j
@Component
public class StateMachineConfigBuilder {


    private final static String MACHINE_ID = "mgOrderStateMachine";
    @Resource
    private FinishAction finishAction;
    @Resource
    private StartDealAction startAction;
    @Resource
    private ErrorAction errorAction;
    @Resource
    private RejectAction rejectAction;


    /**
     * 构建状态机
     *
     * @param beanFactory beanFactory
     * @return StateMachine<OrderStateEnum, OrderEventEnum>
     * @throws Exception Exception
     */
    public StateMachine<States, Events> build(BeanFactory beanFactory, States initState) throws Exception {
        StateMachineBuilder.Builder<States, Events> builder = StateMachineBuilder.builder();

        builder.configureConfiguration()
                .withConfiguration()
                .machineId(MACHINE_ID)
                .listener(new StateMachineListenerAdapter() {
                    @Override
                    public void stateChanged(State from, State to) {
                        log.info("from:{}----to:{}", from, to);
                    }
                })
                .beanFactory(beanFactory);

        builder.configureStates()
                .withStates()
                .initial(initState)
//                .choice(States.DEALING)
                .states(EnumSet.allOf(States.class));

        builder.configureTransitions()

                .withExternal()
                .source(States.TO_DEAL)
                .target(States.DEALING)
                .event(Events.START_DEAL)
                .action(startAction, errorAction)
//                .and()
//                .withChoice()
//                .source(States.DEALING)
//                .first(States.COMPLETED,
//                        context -> context.getMessageHeaders().get("action1") == Events.FINISH,
//                        finishAction)
//                .last(null, null)



                .and()
                .withExternal()
                .source(States.TO_DEAL)
                .target(States.COMPLETED)
                .event(Events.FINISH)
                .action(finishAction, errorAction)

                .and()
                .withExternal()
                .source(States.DEALING)
                .target(States.COMPLETED)
                .event(Events.FINISH)
                .action(finishAction, errorAction)

                .and()
                .withExternal()
                .source(States.DEALING)
                .target(States.REJECT)
                .guard(context -> true)
                .event(Events.REJECT)
                .action(rejectAction, errorAction)


        ;


        return builder.build();
    }

}
