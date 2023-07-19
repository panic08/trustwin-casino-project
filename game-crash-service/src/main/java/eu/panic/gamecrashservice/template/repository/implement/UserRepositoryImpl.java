package eu.panic.gamecrashservice.template.repository.implement;

import eu.panic.gamecrashservice.generatedClasses.tables.UsersTable;
import eu.panic.gamecrashservice.template.repository.UserRepository;
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
}
