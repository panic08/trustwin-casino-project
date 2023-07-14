package eu.panic.managementgameservice.template.repository.implement;

import eu.panic.managementgameservice.generatedClasses.tables.UsersTable;
import eu.panic.managementgameservice.template.enums.Rank;
import eu.panic.managementgameservice.template.repository.UserRepository;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {
    public UserRepositoryImpl(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    private final DSLContext dslContext;
    @Override
    public void updateDataRankById(Rank rank, long id) {
        dslContext.update(UsersTable.USERS_TABLE)
                .set(UsersTable.USERS_TABLE.RANK, rank.toString())
                .where(UsersTable.USERS_TABLE.ID.eq(id))
                .execute();
    }

    @Override
    public void updateBalanceById(long balance, long id) {
        dslContext.update(UsersTable.USERS_TABLE)
                .set(UsersTable.USERS_TABLE.BALANCE, balance)
                .where(UsersTable.USERS_TABLE.ID.eq(id))
                .execute();
    }
}
