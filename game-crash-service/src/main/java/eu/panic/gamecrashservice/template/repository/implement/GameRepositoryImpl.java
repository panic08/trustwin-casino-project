package eu.panic.gamecrashservice.template.repository.implement;

import eu.panic.gamecrashservice.generatedClasses.tables.GamesTable;
import eu.panic.gamecrashservice.template.entity.Game;
import eu.panic.gamecrashservice.template.enums.GameType;
import eu.panic.gamecrashservice.template.repository.GameRepository;
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
    public List<Game> findLastTwentyGamesByGameType(GameType gameType) {
        return dslContext.selectFrom(GamesTable.GAMES_TABLE)
                .where(GamesTable.GAMES_TABLE.GAME_TYPE.eq(gameType.toString()))
                .orderBy(GamesTable.GAMES_TABLE.TIMESTAMP.desc())
                .fetchInto(Game.class);
    }

    @Override
    public Game findLastGameByGameType(GameType gameType) {
        return dslContext.selectFrom(GamesTable.GAMES_TABLE)
                .where(GamesTable.GAMES_TABLE.GAME_TYPE.eq(gameType.toString()))
                .orderBy(GamesTable.GAMES_TABLE.TIMESTAMP.desc())
                .fetchAnyInto(Game.class);
    }

}
