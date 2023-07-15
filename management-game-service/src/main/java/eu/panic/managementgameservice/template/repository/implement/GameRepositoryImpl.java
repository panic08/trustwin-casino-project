package eu.panic.managementgameservice.template.repository.implement;

import eu.panic.managementgameservice.generatedClasses.tables.GamesTable;
import eu.panic.managementgameservice.template.entity.Game;
import eu.panic.managementgameservice.template.repository.GameRepository;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GameRepositoryImpl implements GameRepository {
    public GameRepositoryImpl(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    private final DSLContext dslContext;
    @Override
    public List<Game> findAllByUsername(String username) {
        return dslContext.selectFrom(GamesTable.GAMES_TABLE)
                .where(GamesTable.GAMES_TABLE.USERNAME.eq(username))
                .fetchInto(Game.class);
    }

    @Override
    public List<Game> findLastTen() {
        return dslContext.selectFrom(GamesTable.GAMES_TABLE)
                .orderBy(GamesTable.GAMES_TABLE.TIMESTAMP.desc())
                .limit(5)
                .fetchInto(Game.class);
    }
}
