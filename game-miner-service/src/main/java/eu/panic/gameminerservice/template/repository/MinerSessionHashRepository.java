package eu.panic.gameminerservice.template.repository;

import eu.panic.gameminerservice.template.hash.MinerSessionHash;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@EnableRedisRepositories
public interface MinerSessionHashRepository extends CrudRepository<MinerSessionHash, String> {
    MinerSessionHash findMinerSessionHashByUsername(String username);
}
