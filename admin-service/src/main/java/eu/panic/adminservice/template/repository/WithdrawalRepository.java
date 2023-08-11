package eu.panic.adminservice.template.repository;

import eu.panic.adminservice.template.entity.Withdrawal;
import eu.panic.adminservice.template.enums.WithdrawalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long> {
    List<Withdrawal> findAllByStatusOrderByTimestampDesc(WithdrawalStatus withdrawalStatus);
    @Modifying
    @Query("UPDATE Withdrawal w SET w.status = :status WHERE w.id = :id")
    void updateStatusById(@Param("status") WithdrawalStatus status, @Param("id") long id);
}
