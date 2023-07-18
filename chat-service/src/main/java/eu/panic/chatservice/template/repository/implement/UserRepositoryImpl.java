package eu.panic.chatservice.template.repository.implement;

import eu.panic.chatservice.generatedClasses.tables.UsersTable;
import eu.panic.chatservice.template.entity.User;
import eu.panic.chatservice.template.repository.UserRepository;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {
    public UserRepositoryImpl(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    private final DSLContext dslContext;

    @Override
    public User findByUsername(String username) {
        return dslContext.selectFrom(UsersTable.USERS_TABLE)
                .where(UsersTable.USERS_TABLE.USERNAME.eq(username))
                .fetchOneInto(User.class);
    }
}
