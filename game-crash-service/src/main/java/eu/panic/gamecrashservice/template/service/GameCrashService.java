package eu.panic.gamecrashservice.template.service;

import eu.panic.gamecrashservice.template.entity.Game;
import eu.panic.gamecrashservice.template.payload.GameCrashPlayRequest;
import eu.panic.gamecrashservice.template.payload.GameCrashTakeResponse;

import java.util.List;

public interface GameCrashService {
    void handlePlayCrash(String jwtToken, GameCrashPlayRequest gameCrashPlayRequest);
    GameCrashTakeResponse handleBetTaking(String jwtToken);
    List<Game> getLastTwentyCrashGames();
    Game getLastCrashGame();
}
