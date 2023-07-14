package eu.panic.managementgameservice.template.repository;

import eu.panic.managementgameservice.template.entity.Replenishment;

import java.util.List;

public interface ReplenishmentRepository {
    List<Replenishment> findAllByUsername(String username);
}
