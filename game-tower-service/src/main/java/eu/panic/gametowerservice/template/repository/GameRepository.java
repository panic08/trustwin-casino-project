package eu.panic.gametowerservice.template.repository;

import eu.panic.gametowerservice.template.entity.Game;
import eu.panic.gametowerservice.template.enums.GameType;

public interface GameRepository {
    void save(Game game);
    Game findLastByIdAndGameType(long id, GameType gameType);
}
