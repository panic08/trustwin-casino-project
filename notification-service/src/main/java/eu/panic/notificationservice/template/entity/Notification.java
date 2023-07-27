package eu.panic.notificationservice.template.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "notifications_table")
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "message")
    private String message;
    @Column(name = "is_watched")
    private Boolean isWatched;
    @Column(name = "timestamp")
    private Long timestamp;
}
