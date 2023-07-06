package eu.panic.replenishmentservice.template.repository;

import eu.panic.replenishmentservice.template.hash.CryptoReplenishmentHash;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplenishmentHashRepository extends CrudRepository<CryptoReplenishmentHash, String> {
    List<CryptoReplenishmentHash> findCryptoReplenishmentHashesByUsername(String username);
}
