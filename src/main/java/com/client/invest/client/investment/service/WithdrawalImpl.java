package com.client.invest.client.investment.service;

import com.client.invest.client.investment.enums.ProductType;
import com.client.invest.client.investment.enums.WithdrawalEvents;
import com.client.invest.client.investment.enums.WithdrawalStates;
import com.client.invest.client.investment.exception.InvalidAgeException;
import com.client.invest.client.investment.exception.InvalidWithdrawalAmountException;
import com.client.invest.client.investment.exception.InvalidWithdrawalAmountPercentageException;
import com.client.invest.client.investment.exception.WithdrawalNotFoundException;
import com.client.invest.client.investment.model.Product;
import com.client.invest.client.investment.model.Withdrawal;
import com.client.invest.client.investment.repository.WithdrawalRepository;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class WithdrawalImpl implements IWithdrawal {


    private final WithdrawalRepository withdrawalRepository;
    private final StateMachineFactory<WithdrawalStates, WithdrawalEvents> factory;

    private final IProduct productService;

    private static final String WITHDRAWAL_ID_HEADER = "withdrawalId";

    public WithdrawalImpl(WithdrawalRepository withdrawalRepository,
                          StateMachineFactory<WithdrawalStates, WithdrawalEvents> factory, IProduct product) {
        this.withdrawalRepository = withdrawalRepository;
        this.factory = factory;
        this.productService = product;
    }


    @Override
    public Withdrawal createWithdrawal(Long productId, Double withdrawalAmount) {
        //Validation
        Product clientProduct = productService.getProductById(productId);
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setAmount(withdrawalAmount);
        withdrawal.setProduct(clientProduct);
        withdrawal.setStatus(WithdrawalStates.STARTED);
        validateWithdrawal(withdrawal);
        return withdrawalRepository.save(withdrawal);
    }

    @Override
    public List<Withdrawal> fetchAllWithdrawals() {
        return withdrawalRepository.findAll();
    }

    @Override
    public Withdrawal getWithdrawalById(Long withdrawalId) {
        return withdrawalRepository.findById(withdrawalId).orElseThrow(() -> new WithdrawalNotFoundException(withdrawalId));
    }

    @Override
    public StateMachine<WithdrawalStates, WithdrawalEvents> execute(Long withdrawalId) {
        StateMachine<WithdrawalStates, WithdrawalEvents> sm = this.build(withdrawalId);
        Message<WithdrawalEvents> withdrawalMessage = MessageBuilder.withPayload(WithdrawalEvents.EXECUTE)
                .setHeader(WITHDRAWAL_ID_HEADER, withdrawalId)
                .build();
        sm.sendEvent(withdrawalMessage);
        //Minus the withdrawal amount from product
        Withdrawal withdrawal = withdrawalRepository.findById(withdrawalId).orElseThrow(() -> new WithdrawalNotFoundException(withdrawalId));
        Double currentBalance = withdrawal.getProduct().getCurrentBalance() - withdrawal.getAmount();
        //Save new balance
        productService.updateBalance(withdrawalId, currentBalance);
        return sm;
    }

    @Override
    public StateMachine<WithdrawalStates, WithdrawalEvents> done(Long withdrawalId) {
        StateMachine<WithdrawalStates, WithdrawalEvents> sm = this.build(withdrawalId);
        Message<WithdrawalEvents> deductedMessage = MessageBuilder.withPayload(WithdrawalEvents.DEDUCT)
                .setHeader(WITHDRAWAL_ID_HEADER, withdrawalId)
                .build();
        sm.sendEvent(deductedMessage);
        return sm;
    }

    private StateMachine<WithdrawalStates, WithdrawalEvents> build(Long withdrawalId) {
        Withdrawal withdrawal = withdrawalRepository.findById(withdrawalId).orElseThrow(() -> new WithdrawalNotFoundException(withdrawalId));
        String withdrawalIdKey = Long.toString(withdrawal.getId());

        StateMachine<WithdrawalStates, WithdrawalEvents> sm = this.factory.getStateMachine(withdrawalIdKey);
        sm.stop();
        sm.getStateMachineAccessor()
                .doWithAllRegions(sma -> {
                    sma.addStateMachineInterceptor(new StateMachineInterceptorAdapter<WithdrawalStates, WithdrawalEvents>() {
                        @Override
                        public void preStateChange(State<WithdrawalStates, WithdrawalEvents> state, Message<WithdrawalEvents> message, Transition<WithdrawalStates, WithdrawalEvents> transition, StateMachine<WithdrawalStates, WithdrawalEvents> stateMachine) {
                            //super.preStateChange(state, message, transition, stateMachine);
                            Optional.ofNullable(message).ifPresent(msg -> {
                                Optional.ofNullable((Long) msg.getHeaders().getOrDefault(WITHDRAWAL_ID_HEADER, -1L))
                                        .ifPresent(wd -> {
                                            Withdrawal withdrawal1 = withdrawalRepository.findById(wd).orElseThrow(() -> new WithdrawalNotFoundException(wd));
                                            withdrawal1.setStatus(state.getId());
                                            withdrawalRepository.save(withdrawal1);
                                        });
                            });
                        }
                    });
                    sma.resetStateMachine(new DefaultStateMachineContext<>(withdrawal.getStatus(), null, null, null));
                });
        sm.start();
        return sm;
    }

    private void validateWithdrawal(Withdrawal withdrawal) {
        //if product is retirement check that invest's age is greater than 65
        if (withdrawal.getProduct().getProductType().equals(ProductType.RETIREMENT)) {
            long age = ChronoUnit.YEARS.between(withdrawal.getProduct().getClient().getDateOfBirth(), LocalDate.now());
            if (age < 65) {
                //Throw invalid age exception
                throw new InvalidAgeException(age);
            }
        }
        //that withdrawal amount is not greater than current balance
        if (withdrawal.getAmount() > withdrawal.getProduct().getCurrentBalance()) {
            throw new InvalidWithdrawalAmountException();
        }
        //check that withdrawal amount is not more than 90% of current balance
        Double percentageAmount = (90 * withdrawal.getProduct().getCurrentBalance()) / 100;
        if (withdrawal.getAmount() > percentageAmount) {
            throw new InvalidWithdrawalAmountPercentageException();
        }
    }
}
