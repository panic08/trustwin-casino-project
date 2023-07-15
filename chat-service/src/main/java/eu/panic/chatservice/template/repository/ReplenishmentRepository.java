package eu.panic.chatservice.template.repository;

import eu.panic.chatservice.template.entity.Replenishment;

public interface ReplenishmentRepository {
    Replenishment findLastById(String username);
}
