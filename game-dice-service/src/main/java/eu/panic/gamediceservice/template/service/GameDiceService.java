package eu.panic.gamediceservice.template.service;

import eu.panic.gamediceservice.template.entity.Game;
import eu.panic.gamediceservice.template.payload.GameDicePlayRequest;
import eu.panic.gamediceservice.template.payload.GameDicePlayResponse;

public interface GameDiceService {
    GameDicePlayResponse handlePlayDice(String jwtToken, GameDicePlayRequest gameDicePlayRequest);
    Game getLastDiceGame(String jwtToken);
}
