package eu.panic.chatservice.template.payload;

import eu.panic.chatservice.template.entity.User;
import eu.panic.chatservice.template.enums.MessageType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;
@Getter
@Setter
public class MessageMessage {
    private MessageType type;
    private User user;
    private String message;
    private long timestamp;
}
