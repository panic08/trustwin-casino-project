package eu.paic.managementreplenishmentservice.template.payload;

import eu.paic.managementreplenishmentservice.template.enums.CryptoCurrency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    public static class Exchange{
        private double btcExchange;
        private double ethExchange;
        private double ltcExchange;
        private double trxExchange;
        private double tetherERC20Exchange;
        private double maticExchange;
    }
}
