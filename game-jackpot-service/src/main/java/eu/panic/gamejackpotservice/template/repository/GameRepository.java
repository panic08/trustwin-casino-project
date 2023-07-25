package eu.panic.gamejackpotservice.template.repository;

import eu.panic.gamejackpotservice.template.entity.Game;
import eu.panic.gamejackpotservice.template.enums.GameType;

public interface GameRepository {
    void save(Game game);
    Game findLastGameByGameType(GameType gameType);
}
