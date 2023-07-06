package eu.panic.withdrawalservice.template.payload;

import eu.panic.withdrawalservice.template.enums.WithdrawalMethod;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateWithdrawalRequest {
    private String walletId;
    private WithdrawalMethod method;
    private long amount;
}
