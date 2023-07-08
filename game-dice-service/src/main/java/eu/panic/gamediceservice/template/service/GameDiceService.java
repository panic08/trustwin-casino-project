package eu.panic.gamediceservice.template.service;

import eu.panic.gamediceservice.template.payload.GameDiceRequest;
import eu.panic.gamediceservice.template.payload.GameDiceResponse;

public interface GameDiceService {
    GameDiceResponse handlePlayDice(String jwtToken, GameDiceRequest gameDiceRequest);
}
