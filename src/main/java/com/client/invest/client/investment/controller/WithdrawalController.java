package com.client.invest.client.investment.controller;

import com.client.invest.client.investment.controller.model.WithdrawalRequest;
import com.client.invest.client.investment.controller.model.WithdrawalResponse;
import com.client.invest.client.investment.enums.WithdrawalEvents;
import com.client.invest.client.investment.enums.WithdrawalStates;
import com.client.invest.client.investment.model.Withdrawal;
import com.client.invest.client.investment.service.IWithdrawal;
import com.client.invest.client.investment.util.WithdrawalModelMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Tag(name = "Withdrawal Process API")
public class WithdrawalController {

    Logger logger = LoggerFactory.getLogger(WithdrawalController.class);
    private final IWithdrawal withdrawalService;

    public WithdrawalController(IWithdrawal withdrawalService) {
        this.withdrawalService = withdrawalService;
    }

    @Operation(summary = "Create withdrawal",
            description = "Create withdrawal for an investment product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Invalid age validation"),
            @ApiResponse(responseCode = "400", description = "Invalid withdrawal amount"),
            @ApiResponse(responseCode = "400", description = "Invalid amount percentage")
    })
    @PostMapping("/withdrawal")
    public ResponseEntity<WithdrawalResponse> createWithdrawal(@Valid @RequestBody WithdrawalRequest withdrawalRequest) {
        Withdrawal withdrawal = withdrawalService.createWithdrawal(withdrawalRequest.getProductId(), withdrawalRequest.getAmount());
        return new ResponseEntity<>(WithdrawalModelMapper.mapToResponse(withdrawal), HttpStatus.OK);
    }

    @Operation(summary = "Execute withdrawal",
            description = "Execute the withdrawal for an investment product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Withdrawal not found exception")
    })
    @GetMapping("/withdrawal/{withdrawalId}/execute")
    public ResponseEntity<WithdrawalResponse> executeWithdrawal(@Parameter(name = "withdrawalId", description = "withdrawal id", example = "1")
                                                                @PathVariable("withdrawalId") Long withdrawalId) {
        //Call execute
        StateMachine<WithdrawalStates, WithdrawalEvents> withdrawalStateMachine = withdrawalService.execute(withdrawalId);
        logger.info("after calling execute(): " + withdrawalStateMachine.getState().getId().name());

        //Call done
        return new ResponseEntity<>(WithdrawalModelMapper.mapToResponse(withdrawalService.getWithdrawalById(withdrawalId)), HttpStatus.OK);
    }

    @Operation(summary = "Finish withdrawal",
            description = "Complete the withdrawal for an investment product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Withdrawal not found exception")
    })
    @GetMapping("/withdrawal/{withdrawalId}/done")
    public ResponseEntity<WithdrawalResponse> withdrawalDone(@Parameter(name = "withdrawalId", description = "withdrawal id", example = "1")
                                                             @PathVariable("withdrawalId") Long withdrawalId) {
        //Call execute
        StateMachine<WithdrawalStates, WithdrawalEvents> withdrawalStateMachine = withdrawalService.done(withdrawalId);
        logger.info("after calling done(): " + withdrawalStateMachine.getState().getId().name());

        //Call done
        return new ResponseEntity<>(WithdrawalModelMapper.mapToResponse(withdrawalService.getWithdrawalById(withdrawalId)), HttpStatus.OK);
    }

    @Operation(summary = "Get all withdrawal",
            description = "Fetch all withdrawals")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved")
    })
    @GetMapping("/withdrawal")
    public ResponseEntity<List<WithdrawalResponse>> getAllWithdrawals() {
        return new ResponseEntity<>(WithdrawalModelMapper.mapResponseList(withdrawalService.fetchAllWithdrawals()), HttpStatus.OK);
    }
}
