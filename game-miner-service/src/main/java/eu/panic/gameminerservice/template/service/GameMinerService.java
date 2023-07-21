package eu.panic.gameminerservice.template.service;

import eu.panic.gameminerservice.template.entity.Game;
import eu.panic.gameminerservice.template.hash.GameMinerSessionHash;
import eu.panic.gameminerservice.template.payload.GameMinerCreateRequest;
import eu.panic.gameminerservice.template.payload.GameMinerCreateResponse;
import eu.panic.gameminerservice.template.payload.GameMinerPlayRequest;
import eu.panic.gameminerservice.template.payload.GameMinerPlayResponse;

public interface GameMinerService {
    GameMinerCreateResponse handleCreatingMinerSession(String jwtToken, GameMinerCreateRequest gameMinerCreateRequest);
    GameMinerPlayResponse handlePlayMiner(String jwtToken, GameMinerPlayRequest gameMinerPlayRequest);
    GameMinerPlayResponse handleBetTaking(String jwtToken);
    GameMinerSessionHash getCurrentMinerGame(String jwtToken);
    Game getLastMinerGame(String jwtToken);
}
