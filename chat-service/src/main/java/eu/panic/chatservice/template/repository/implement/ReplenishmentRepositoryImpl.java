package eu.panic.chatservice.template.repository.implement;

import eu.panic.chatservice.generatedClasses.tables.ReplenishmentsTable;
import eu.panic.chatservice.template.entity.Replenishment;
import eu.panic.chatservice.template.repository.ReplenishmentRepository;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
public class ReplenishmentRepositoryImpl implements ReplenishmentRepository {
    public ReplenishmentRepositoryImpl(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    private final DSLContext dslContext;

    @Override
    public Replenishment findLastByUsername(String username) {
        return dslContext.selectFrom(ReplenishmentsTable.REPLENISHMENTS_TABLE)
                .where(ReplenishmentsTable.REPLENISHMENTS_TABLE.USERNAME.eq(username))
                .orderBy(ReplenishmentsTable.REPLENISHMENTS_TABLE.TIMESTAMP.desc())
                .fetchAnyInto(Replenishment.class);

    }
}
