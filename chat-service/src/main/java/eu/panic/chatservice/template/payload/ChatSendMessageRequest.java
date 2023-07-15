package eu.panic.chatservice.template.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatSendMessageRequest {
    private String message;
}
