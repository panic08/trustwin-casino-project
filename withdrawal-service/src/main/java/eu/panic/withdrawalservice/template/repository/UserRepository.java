package eu.panic.withdrawalservice.template.repository;

import eu.panic.withdrawalservice.template.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Modifying
    @Query("UPDATE User u SET u.balance = :balance WHERE u.username = :username")
    void updateBalanceByUsername(@Param("balance") Long balance, @Param("username") String username);
}
