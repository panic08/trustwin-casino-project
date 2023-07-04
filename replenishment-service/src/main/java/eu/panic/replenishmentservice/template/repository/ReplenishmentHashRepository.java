package eu.panic.replenishmentservice.template.repository;

import eu.panic.replenishmentservice.template.hash.CryptoReplenishmentHash;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplenishmentHashRepository extends CrudRepository<CryptoReplenishmentHash, String> {
}
