package eu.panic.gamecrashservice.template.service;

import eu.panic.gamecrashservice.template.payload.GameCrashPlayRequest;

public interface GameCrashService {
    void handlePlayCrash(String jwtToken, GameCrashPlayRequest gameCrashPlayRequest);
}
