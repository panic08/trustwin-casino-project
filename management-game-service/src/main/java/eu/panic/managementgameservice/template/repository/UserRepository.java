package eu.panic.managementgameservice.template.repository;

import eu.panic.managementgameservice.template.enums.Rank;

public interface UserRepository {
    void updateDataRankById(Rank rank, long id);
    void updateBalanceById(long balance, long id);
}
