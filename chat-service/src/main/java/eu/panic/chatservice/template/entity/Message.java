package eu.panic.chatservice.template.entity;

import eu.panic.chatservice.template.enums.MessageType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "messages_table")
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = true)
    private Long id;
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private MessageType type;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "message", nullable = false)
    private String message;
    @Column(name = "timestamp", nullable = false)
    private Long timestamp;
}
