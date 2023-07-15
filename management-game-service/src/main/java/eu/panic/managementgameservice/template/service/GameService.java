package eu.panic.managementgameservice.template.service;

import eu.panic.managementgameservice.template.payload.GameMessage;

import java.util.List;

public interface GameService {
    void handleGameMessage(GameMessage gameMessage);
    List<GameMessage> getAllGameMessages();
}
