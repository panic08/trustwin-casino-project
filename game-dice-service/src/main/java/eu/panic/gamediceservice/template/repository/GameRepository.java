package eu.panic.gamediceservice.template.repository;

import eu.panic.gamediceservice.template.entity.Game;
import eu.panic.gamediceservice.template.enums.GameType;

public interface GameRepository {
    void save(Game game);
    Game findGameByUsernameAndGameTypeOrderDesc(String username, GameType gameType);
}
