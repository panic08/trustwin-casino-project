package eu.panic.gamediceservice.template.repository.implement;

import eu.panic.gamediceservice.generatedClasses.tables.UsersTable;
import eu.panic.gamediceservice.template.repository.UserRepository;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {
    public UserRepositoryImpl(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    private final DSLContext dslContext;
    @Override
    public void updateBalanceById(long balance, long id) {
        dslContext.update(UsersTable.USERS_TABLE)
                .set(UsersTable.USERS_TABLE.BALANCE, balance)
                .where(UsersTable.USERS_TABLE.ID.eq(id))
                .execute();
    }

    @Override
    public void updateClientSeedAndServerSeedById(String clientSeed, String serverSeed, long id) {
        dslContext.update(UsersTable.USERS_TABLE)
                .set(UsersTable.USERS_TABLE.CLIENT_SEED, clientSeed)
                .set(UsersTable.USERS_TABLE.SERVER_SEED, serverSeed)
                .where(UsersTable.USERS_TABLE.ID.eq(id))
                .execute();
    }
}
