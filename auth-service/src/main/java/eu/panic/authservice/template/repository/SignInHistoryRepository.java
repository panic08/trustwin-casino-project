package eu.panic.authservice.template.repository;

import eu.panic.authservice.template.entity.SignInHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignInHistoryRepository extends JpaRepository<SignInHistory, Long> {
}
