package eu.panic.adminservice.template.service;

import eu.panic.adminservice.template.entity.Withdrawal;
import eu.panic.adminservice.template.enums.WithdrawalStatus;

import java.util.List;

public interface AdminService {
    List<Withdrawal> getAllWithdrawalsByWithdrawalStatus(String jwtToken, WithdrawalStatus withdrawalStatus);
    void updateWithdrawalStatusById(String jwtToken, WithdrawalStatus withdrawalStatus, long id);
    void handleBlockUserById(String jwtToken, long id);
    void handleUnblockUserById(String jwtToken, long id);
}
