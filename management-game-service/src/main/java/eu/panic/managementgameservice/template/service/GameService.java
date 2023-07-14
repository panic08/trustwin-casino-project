package eu.panic.managementgameservice.template.service;

import eu.panic.managementgameservice.template.payload.GameMessage;

public interface GameService {
    void handleGameMessage(GameMessage gameMessage);
}
