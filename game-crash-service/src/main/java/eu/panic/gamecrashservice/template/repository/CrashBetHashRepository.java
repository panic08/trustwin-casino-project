package eu.panic.gamecrashservice.template.repository;

import eu.panic.gamecrashservice.template.hash.CrashBetHash;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrashBetHashRepository extends CrudRepository<CrashBetHash, String> {
    CrashBetHash findCrashBetHashByUsername(String username);
}
