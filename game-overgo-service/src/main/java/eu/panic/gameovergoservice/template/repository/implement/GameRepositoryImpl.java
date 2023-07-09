package eu.panic.gameovergoservice.template.repository.implement;

import eu.panic.gameovergoservice.generatedClasses.tables.GamesTable;
import eu.panic.gameovergoservice.template.entity.Game;
import eu.panic.gameovergoservice.template.enums.GameType;
import eu.panic.gameovergoservice.template.repository.GameOvergoRepository;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
public class GameRepositoryImpl implements GameOvergoRepository {
    public GameRepositoryImpl(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    private final DSLContext dslContext;
    @Override
    public void save(Game game) {
        dslContext.insertInto(GamesTable.GAMES_TABLE)
                .set(GamesTable.GAMES_TABLE.USERNAME, game.getUsername())
                .set(GamesTable.GAMES_TABLE.NICKNAME, game.getNickname())
                .set(GamesTable.GAMES_TABLE.GAME_TYPE, game.getGameType().toString())
                .set(GamesTable.GAMES_TABLE.BET, game.getBet())
                .set(GamesTable.GAMES_TABLE.WIN, game.getWin())
                .set(GamesTable.GAMES_TABLE.COEFFICIENT, game.getCoefficient())
                .set(GamesTable.GAMES_TABLE.CLIENT_SEED, game.getClientSeed())
                .set(GamesTable.GAMES_TABLE.SERVER_SEED, game.getServerSeed())
                .set(GamesTable.GAMES_TABLE.SALT, game.getSalt())
                .set(GamesTable.GAMES_TABLE.TIMESTAMP, game.getTimestamp())
                .execute();
    }

    @Override
    public Game findGameByUsernameAndGameTypeOrderDesc(String username, GameType gameType) {
        return dslContext.selectFrom(GamesTable.GAMES_TABLE)
                .where(GamesTable.GAMES_TABLE.USERNAME.eq(username))
                .and(GamesTable.GAMES_TABLE.GAME_TYPE.eq(gameType.toString()))
                .orderBy(GamesTable.GAMES_TABLE.TIMESTAMP.desc())
                .fetchOneInto(Game.class);
    }
}
