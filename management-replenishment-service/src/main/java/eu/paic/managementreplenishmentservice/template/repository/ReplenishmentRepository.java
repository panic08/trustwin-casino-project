package eu.paic.managementreplenishmentservice.template.repository;

import eu.paic.managementreplenishmentservice.template.entity.Replenishment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplenishmentRepository extends JpaRepository<Replenishment, Long> {
}
