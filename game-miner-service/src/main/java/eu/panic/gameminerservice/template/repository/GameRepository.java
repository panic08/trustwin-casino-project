package eu.panic.gameminerservice.template.repository;

import eu.panic.gameminerservice.template.entity.Game;
import eu.panic.gameminerservice.template.enums.GameType;

public interface GameRepository {
    void save(Game game);
    Game findGameByUsernameAndGameTypeOrderDesc(String username, GameType gameType);
}
