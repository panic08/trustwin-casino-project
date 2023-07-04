package eu.panic.replenishmentservice.template.payload;

import eu.panic.replenishmentservice.template.enums.CryptoCurrency;

public record CryptoReplenishmentMessage(
        String username,
        String walletId,
        double amount,
        CryptoCurrency currency,
        long timestamp
) {
}
