package eu.panic.gamecrashservice.template.repository.implement;

import eu.panic.gamecrashservice.template.repository.GameRepository;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
public class GameRepositoryImpl implements GameRepository {
    public GameRepositoryImpl(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    private final DSLContext dslContext;
}
