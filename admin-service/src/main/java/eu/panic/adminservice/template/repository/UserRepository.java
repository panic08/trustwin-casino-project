package eu.panic.adminservice.template.repository;

import eu.panic.adminservice.template.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Modifying
    @Query("UPDATE User u SET u.isAccountNonLocked = :isAccountNonLocked WHERE u.id = :id")
    void updateIsAccountNonLocked(@Param("isAccountNonLocked") boolean isAccountNonLocked, @Param("id") long id);
}
