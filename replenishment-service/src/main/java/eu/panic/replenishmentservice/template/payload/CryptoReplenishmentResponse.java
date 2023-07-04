package eu.panic.replenishmentservice.template.payload;

import eu.panic.replenishmentservice.template.enums.CryptoCurrency;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CryptoReplenishmentResponse {
    private String walletId;
    private double amount;
    private CryptoCurrency currency;
    private long timestamp;

}
