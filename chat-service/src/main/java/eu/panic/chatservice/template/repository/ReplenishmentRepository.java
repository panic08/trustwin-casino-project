package eu.panic.chatservice.template.repository;

import eu.panic.chatservice.template.entity.Replenishment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplenishmentRepository extends JpaRepository<Replenishment, Long> {
    Replenishment findTopByUsernameOrderByTimestampDesc(String username);
}

