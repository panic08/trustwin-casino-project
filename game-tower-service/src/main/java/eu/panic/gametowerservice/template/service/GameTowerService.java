package eu.panic.gametowerservice.template.service;

import eu.panic.gametowerservice.template.entity.Game;
import eu.panic.gametowerservice.template.payload.*;

public interface GameTowerService {
    GameTowerCreateResponse createTowerSession(String jwtToken, GameTowerCreateRequest gameTowerCreateRequest);
    GameTowerPlayResponse handlePlayTower(String jwtToken, GameTowerPlayRequest gameTowerPlayRequest);
    GameTowerPlayResponse handleBetTaking(String jwtToken);
    GameTowerGetCurrentResponse getCurrentTowerGame(String jwtToken);
    Game getLastTowerGame(String jwtToken);
}
