package eu.panic.chatservice.template.service;

import eu.panic.chatservice.template.entity.Message;
import eu.panic.chatservice.template.payload.ChatSendMessageRequest;

import java.util.List;

public interface ChatService {
    void sendMessage(String jwtToken, ChatSendMessageRequest chatSendMessageRequest);
    List<Message> getAllMessages();
}
