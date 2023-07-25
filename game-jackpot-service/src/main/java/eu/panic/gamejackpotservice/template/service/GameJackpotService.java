package eu.panic.gamejackpotservice.template.service;

import eu.panic.gamejackpotservice.template.entity.Game;
import eu.panic.gamejackpotservice.template.enums.JackpotRoom;
import eu.panic.gamejackpotservice.template.hash.GameJackpotBetHash;
import eu.panic.gamejackpotservice.template.payload.GameJackpotPlayRequest;

import java.util.List;

public interface GameJackpotService {
    void handlePlayJackpot(String jwtToken, GameJackpotPlayRequest gameJackpotPlayRequest);
    Game getLastJackpotGame(JackpotRoom room);
    List<GameJackpotBetHash> getAllJackpotBets(JackpotRoom room);
}
