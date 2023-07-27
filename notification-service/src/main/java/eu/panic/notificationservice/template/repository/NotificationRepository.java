package eu.panic.notificationservice.template.repository;

import eu.panic.notificationservice.template.entity.Notification;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findTop5ByUsernameOrderByTimestampDesc(String username);
    @Transactional
    @Modifying
    @Query("UPDATE Notification n SET n.isWatched = true WHERE n.username = :username AND n.id IN (SELECT nn.id FROM Notification nn WHERE nn.username = :username ORDER BY nn.timestamp DESC)")
    void updateLast5NotificationsToWatched(String username);
}
