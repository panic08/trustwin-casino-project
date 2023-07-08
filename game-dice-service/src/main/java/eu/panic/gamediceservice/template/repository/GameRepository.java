package eu.panic.gamediceservice.template.repository;

import eu.panic.gamediceservice.template.entity.Game;

public interface GameRepository {
    void save(Game game);
}
