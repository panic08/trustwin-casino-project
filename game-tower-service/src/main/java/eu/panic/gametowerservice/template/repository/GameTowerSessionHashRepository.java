package eu.panic.gametowerservice.template.repository;

import eu.panic.gametowerservice.template.hash.GameTowerSessionHash;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameTowerSessionHashRepository extends CrudRepository<GameTowerSessionHash, String> {
    GameTowerSessionHash findGameTowerSessionHashByUsername(String username);
}
