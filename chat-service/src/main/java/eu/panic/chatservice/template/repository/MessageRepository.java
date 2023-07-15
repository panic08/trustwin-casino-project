package eu.panic.chatservice.template.repository;

import eu.panic.chatservice.template.entity.Message;

import java.util.List;

public interface MessageRepository {
    void save(Message message);
    List<Message> findLastFiftyMessages();
}
