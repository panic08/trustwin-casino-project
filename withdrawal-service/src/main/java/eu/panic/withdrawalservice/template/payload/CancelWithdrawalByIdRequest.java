package eu.panic.withdrawalservice.template.payload;

import lombok.Getter;

@Getter
public class CancelWithdrawalByIdRequest {
    private long id;
}
