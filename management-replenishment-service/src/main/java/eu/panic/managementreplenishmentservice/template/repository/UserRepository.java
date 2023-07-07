package eu.panic.managementreplenishmentservice.template.repository;

import eu.panic.managementreplenishmentservice.template.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
    User findUserByRefData_RefLink(String refLink);
}
