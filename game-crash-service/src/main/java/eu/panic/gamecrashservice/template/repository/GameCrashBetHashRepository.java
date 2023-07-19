package eu.panic.gamecrashservice.template.repository;

import eu.panic.gamecrashservice.template.hash.GameCrashBetHash;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameCrashBetHashRepository extends CrudRepository<GameCrashBetHash, String> {
    GameCrashBetHash findCrashBetHashByUsername(String username);
    List<GameCrashBetHash> findAllByIsTaken(boolean isTaken);
}
