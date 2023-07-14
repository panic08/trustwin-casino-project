package eu.panic.managementgameservice.template.repository.implement;

import eu.panic.managementgameservice.generatedClasses.tables.ReplenishmentsTable;
import eu.panic.managementgameservice.template.entity.Replenishment;
import eu.panic.managementgameservice.template.repository.ReplenishmentRepository;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReplenishmentRepositoryImpl implements ReplenishmentRepository {
    public ReplenishmentRepositoryImpl(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    private final DSLContext dslContext;
    @Override
    public List<Replenishment> findAllByUsername(String username) {
        return dslContext.selectFrom(ReplenishmentsTable.REPLENISHMENTS_TABLE)
                .where(ReplenishmentsTable.REPLENISHMENTS_TABLE.USERNAME.eq(username))
                .fetchInto(Replenishment.class);
    }
}
