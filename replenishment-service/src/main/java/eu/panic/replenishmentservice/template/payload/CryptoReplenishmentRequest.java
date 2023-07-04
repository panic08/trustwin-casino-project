package eu.panic.replenishmentservice.template.payload;

import eu.panic.replenishmentservice.template.enums.Currency;

public record CryptoReplenishmentRequest(
        double amount,
        Currency currency
) {
}
