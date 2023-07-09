package eu.panic.gameovergoservice.template.repository;

import eu.panic.gameovergoservice.template.entity.Game;
import eu.panic.gameovergoservice.template.enums.GameType;

public interface GameOvergoRepository {
    void save(Game game);
    Game findGameByUsernameAndGameTypeOrderDesc(String username, GameType gameType);
}
