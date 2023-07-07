package eu.panic.managementreplenishmentservice.template.service;

import eu.panic.managementreplenishmentservice.template.payload.CryptoReplenishmentMessage;

public interface CryptoReplenishmentService {
    void handleReplenishment(CryptoReplenishmentMessage cryptoReplenishmentMessage);
}
