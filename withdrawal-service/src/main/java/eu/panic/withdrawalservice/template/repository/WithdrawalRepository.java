package eu.panic.withdrawalservice.template.repository;

import eu.panic.withdrawalservice.template.entity.Withdrawal;
import eu.panic.withdrawalservice.template.enums.WithdrawalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long> {
    List<Withdrawal> findAllByUsername(String username);
    @Modifying
    @Query("UPDATE Withdrawal w SET w.status = :status WHERE w.id = :id")
    void updateStatusById(@Param("status") WithdrawalStatus status, @Param("id") Long id);
    @Query("SELECT w.amount FROM Withdrawal w WHERE w.id = :id")
    Long findAmountById(Long id);
}
