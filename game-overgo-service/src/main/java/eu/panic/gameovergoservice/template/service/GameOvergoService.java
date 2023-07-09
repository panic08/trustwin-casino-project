package eu.panic.gameovergoservice.template.service;

import eu.panic.gameovergoservice.template.entity.Game;
import eu.panic.gameovergoservice.template.payload.GameOvergoPlayRequest;
import eu.panic.gameovergoservice.template.payload.GameOvergoPlayResponse;

public interface GameOvergoService {
    GameOvergoPlayResponse handlePlayOvergo(String jwtToken, GameOvergoPlayRequest gameOvergoPlayRequest);
    Game getLastOvergoGame(String jwtToken);
}
