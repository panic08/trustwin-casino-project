package eu.panic.replenishmentservice.template.service;

import eu.panic.replenishmentservice.template.enums.CryptoCurrency;
import eu.panic.replenishmentservice.template.hash.CryptoReplenishmentHash;
import eu.panic.replenishmentservice.template.payload.CryptoReplenishmentRequest;
import eu.panic.replenishmentservice.template.payload.CryptoReplenishmentResponse;

public interface CryptoReplenishmentService {
    CryptoReplenishmentResponse handlePayByBitcoin(String jwtToken, CryptoReplenishmentRequest cryptoReplenishmentRequest);
    CryptoReplenishmentResponse handlePayByEthereum(String jwtToken, CryptoReplenishmentRequest cryptoReplenishmentRequest);
    CryptoReplenishmentResponse handlePayByLitecoin(String jwtToken, CryptoReplenishmentRequest cryptoReplenishmentRequest);
    CryptoReplenishmentResponse handlePayByTron(String jwtToken, CryptoReplenishmentRequest cryptoReplenishmentRequest);
    CryptoReplenishmentResponse handlePayByTetherERC20(String jwtToken, CryptoReplenishmentRequest cryptoReplenishmentRequest);
    CryptoReplenishmentResponse handlePayByPolygon(String jwtToken, CryptoReplenishmentRequest cryptoReplenishmentRequest);
    CryptoReplenishmentHash getCryptoPayment(String jwtToken, CryptoCurrency currency);
}
