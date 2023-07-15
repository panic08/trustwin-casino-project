package eu.panic.managementgameservice.template.repository;

import eu.panic.managementgameservice.template.entity.Game;

import java.util.List;

public interface GameRepository {
    List<Game> findAllByUsername(String username);
    List<Game> findLastTen();
}
