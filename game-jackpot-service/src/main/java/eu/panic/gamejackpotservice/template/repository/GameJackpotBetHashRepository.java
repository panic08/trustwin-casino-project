package eu.panic.gamejackpotservice.template.repository;

import eu.panic.gamejackpotservice.template.enums.JackpotRoom;
import eu.panic.gamejackpotservice.template.hash.GameJackpotBetHash;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameJackpotBetHashRepository extends CrudRepository<GameJackpotBetHash, String> {
    List<GameJackpotBetHash> findAllByRoom(JackpotRoom room);
}
