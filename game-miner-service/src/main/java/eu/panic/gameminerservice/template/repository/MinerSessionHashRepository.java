package eu.panic.gameminerservice.template.repository;

import eu.panic.gameminerservice.template.hash.GameMinerSessionHash;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@EnableRedisRepositories
public interface MinerSessionHashRepository extends CrudRepository<GameMinerSessionHash, String> {
    GameMinerSessionHash findMinerSessionHashByUsername(String username);
}
