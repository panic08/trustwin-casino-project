package eu.panic.chatservice.template.repository;

import eu.panic.chatservice.template.entity.User;

public interface UserRepository {
    User findByUsername(String username);
}
