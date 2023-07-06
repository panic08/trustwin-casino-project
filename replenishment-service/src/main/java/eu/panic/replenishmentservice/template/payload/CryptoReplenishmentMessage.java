package eu.panic.replenishmentservice.template.payload;

import eu.panic.replenishmentservice.template.enums.CryptoCurrency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public record CryptoReplenishmentMessage(
        String username,
        String walletId,
        double amount,
        Exchange exchange,
        CryptoCurrency currency,
        long timestamp
) {
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Exchange{
        private double btcExchange;
        private double ethExchange;
        private double ltcExchange;
        private double trxExchange;
        private double tetherERC20Exchange;
        private double maticExchange;
    }
}
