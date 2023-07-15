package eu.panic.chatservice.template.entity;

import eu.panic.chatservice.template.enums.MessageType;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;

@Data
public class Message {
    @Column("id")
    private Long id;
    @Column("type")
    private MessageType type;
    @Column("username")
    private String username;
    @Column("message")
    private String message;
    @Column("timestamp")
    private Long timestamp;
}
