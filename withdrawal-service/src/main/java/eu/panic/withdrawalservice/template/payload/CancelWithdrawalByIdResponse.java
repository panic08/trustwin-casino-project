package eu.panic.withdrawalservice.template.payload;

import eu.panic.withdrawalservice.template.enums.WithdrawalMethod;
import eu.panic.withdrawalservice.template.enums.WithdrawalStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CancelWithdrawalByIdResponse {
    private WithdrawalStatus status;
    private WithdrawalMethod method;
    private long amount;
    private String walletId;
    private long timestamp;
}
