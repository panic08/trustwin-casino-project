package eu.panic.withdrawalservice.template.service;

import eu.panic.withdrawalservice.template.entity.Withdrawal;
import eu.panic.withdrawalservice.template.payload.CancelWithdrawalByIdRequest;
import eu.panic.withdrawalservice.template.payload.CancelWithdrawalByIdResponse;
import eu.panic.withdrawalservice.template.payload.CreateWithdrawalRequest;
import eu.panic.withdrawalservice.template.payload.CreateWithdrawalResponse;

import java.util.List;

public interface WithdrawalService {
    CreateWithdrawalResponse createWithdrawal(String jwtToken, CreateWithdrawalRequest createWithdrawalRequest);
    List<Withdrawal> getWithdrawalsByUsername(String jwtToken);
    CancelWithdrawalByIdResponse cancelWithdrawalById(String jwtToken, CancelWithdrawalByIdRequest cancelWithdrawalByIdRequest);

}
