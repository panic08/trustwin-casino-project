package eu.panic.gamecrashservice.template.repository;

import eu.panic.gamecrashservice.template.entity.Game;
import eu.panic.gamecrashservice.template.enums.GameType;

import java.util.List;

public interface GameRepository {
    void save(Game game);
    List<Game> findLastTwentyGamesByGameType(GameType gameType);
    Game findLastGameByGameType(GameType gameType);
}
