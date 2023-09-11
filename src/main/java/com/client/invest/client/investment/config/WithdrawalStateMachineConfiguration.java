package com.client.invest.client.investment.config;

import com.client.invest.client.investment.enums.WithdrawalEvents;
import com.client.invest.client.investment.enums.WithdrawalStates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

@Configuration
@EnableStateMachineFactory
public class WithdrawalStateMachineConfiguration extends StateMachineConfigurerAdapter<WithdrawalStates, WithdrawalEvents> {
    Logger logger = LoggerFactory.getLogger(WithdrawalStateMachineConfiguration.class);

    @Override
    public void configure(StateMachineTransitionConfigurer<WithdrawalStates, WithdrawalEvents> transitions) throws Exception {
        //super.configure(transitions);
        transitions
                .withExternal().source(WithdrawalStates.STARTED).target(WithdrawalStates.EXECUTING).event(WithdrawalEvents.EXECUTE)
                .and()
                .withExternal().source(WithdrawalStates.EXECUTING).target(WithdrawalStates.DONE).event(WithdrawalEvents.DEDUCT);
    }

    @Override
    public void configure(StateMachineStateConfigurer<WithdrawalStates, WithdrawalEvents> states) throws Exception {
        //super.configure(states);
        states
                .withStates()
                .initial(WithdrawalStates.STARTED)
                .state(WithdrawalStates.EXECUTING)
                .end(WithdrawalStates.DONE);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<WithdrawalStates, WithdrawalEvents> config) throws Exception {
        //super.configure(config);
        StateMachineListenerAdapter<WithdrawalStates, WithdrawalEvents> adapter = new StateMachineListenerAdapter<WithdrawalStates, WithdrawalEvents>() {
            @Override
            public void stateChanged(State<WithdrawalStates, WithdrawalEvents> from, State<WithdrawalStates, WithdrawalEvents> to) {
                logger.info(String.format("stateChanged(from: %s, to: %s)", from + "", to + ""));
            }
        };
        config.withConfiguration().autoStartup(false).listener(adapter);
    }
}
