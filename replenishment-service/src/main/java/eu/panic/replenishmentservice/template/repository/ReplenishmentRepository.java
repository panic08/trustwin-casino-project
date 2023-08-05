package eu.panic.replenishmentservice.template.repository;

import eu.panic.replenishmentservice.template.entity.Replenishment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplenishmentRepository extends JpaRepository<Replenishment, Long> {
    List<Replenishment> findAllByUsername(String username);
}
