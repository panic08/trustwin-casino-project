/*
 * This file is generated by jOOQ.
 */
package eu.panic.gameminerservice.generatedClasses;


import eu.panic.gameminerservice.generatedClasses.tables.FlywaySchemaHistory;
import eu.panic.gameminerservice.generatedClasses.tables.GamesTable;
import eu.panic.gameminerservice.generatedClasses.tables.ReplenishmentsTable;
import eu.panic.gameminerservice.generatedClasses.tables.SignInHistoryTable;
import eu.panic.gameminerservice.generatedClasses.tables.UsersTable;
import eu.panic.gameminerservice.generatedClasses.tables.WithdrawalsTable;

import java.util.Arrays;
import java.util.List;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Public extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public</code>
     */
    public static final Public PUBLIC = new Public();

    /**
     * The table <code>public.flyway_schema_history</code>.
     */
    public final FlywaySchemaHistory FLYWAY_SCHEMA_HISTORY = FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY;

    /**
     * The table <code>public.games_table</code>.
     */
    public final GamesTable GAMES_TABLE = GamesTable.GAMES_TABLE;

    /**
     * The table <code>public.replenishments_table</code>.
     */
    public final ReplenishmentsTable REPLENISHMENTS_TABLE = ReplenishmentsTable.REPLENISHMENTS_TABLE;

    /**
     * The table <code>public.sign_in_history_table</code>.
     */
    public final SignInHistoryTable SIGN_IN_HISTORY_TABLE = SignInHistoryTable.SIGN_IN_HISTORY_TABLE;

    /**
     * The table <code>public.users_table</code>.
     */
    public final UsersTable USERS_TABLE = UsersTable.USERS_TABLE;

    /**
     * The table <code>public.withdrawals_table</code>.
     */
    public final WithdrawalsTable WITHDRAWALS_TABLE = WithdrawalsTable.WITHDRAWALS_TABLE;

    /**
     * No further instances allowed
     */
    private Public() {
        super("public", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.asList(
            FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY,
            GamesTable.GAMES_TABLE,
            ReplenishmentsTable.REPLENISHMENTS_TABLE,
            SignInHistoryTable.SIGN_IN_HISTORY_TABLE,
            UsersTable.USERS_TABLE,
            WithdrawalsTable.WITHDRAWALS_TABLE
        );
    }
}