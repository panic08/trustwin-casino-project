package eu.panic.withdrawalservice.template.controller;

import eu.panic.withdrawalservice.template.entity.Withdrawal;
import eu.panic.withdrawalservice.template.payload.CancelWithdrawalByIdRequest;
import eu.panic.withdrawalservice.template.payload.CancelWithdrawalByIdResponse;
import eu.panic.withdrawalservice.template.payload.CreateWithdrawalRequest;
import eu.panic.withdrawalservice.template.payload.CreateWithdrawalResponse;
import eu.panic.withdrawalservice.template.service.implement.WithdrawalServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/withdrawal")
public class WithdrawalController {
    public WithdrawalController(WithdrawalServiceImpl withdrawalService) {
        this.withdrawalService = withdrawalService;
    }

    private final WithdrawalServiceImpl withdrawalService;
    @GetMapping("/getByUsername")
    private List<Withdrawal> getWithdrawalsByUsername(@RequestHeader String jwtToken){
        return withdrawalService.getWithdrawalsByUsername(jwtToken);
    }
    @PostMapping("/create")
    private CreateWithdrawalResponse createWithdrawal(
            @RequestHeader String jwtToken,
            @RequestBody CreateWithdrawalRequest createWithdrawalRequest
            ){
        return withdrawalService.createWithdrawal(jwtToken, createWithdrawalRequest);
    }
    @PutMapping("/cancelById")
    private CancelWithdrawalByIdResponse cancelWithdrawal(
            @RequestHeader String jwtToken,
            @RequestBody CancelWithdrawalByIdRequest cancelWithdrawalByIdRequest
            ){
        return withdrawalService.cancelWithdrawalById(jwtToken, cancelWithdrawalByIdRequest);
    }
}
