package eu.paic.managementreplenishmentservice.template.service;

import eu.paic.managementreplenishmentservice.template.payload.CryptoReplenishmentMessage;

public interface CryptoReplenishmentService {
    void handleReplenishment(CryptoReplenishmentMessage cryptoReplenishmentMessage);
}
