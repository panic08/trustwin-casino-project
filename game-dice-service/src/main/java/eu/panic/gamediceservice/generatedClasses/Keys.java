/*
 * This file is generated by jOOQ.
 */
package eu.panic.gamediceservice.generatedClasses;


import eu.panic.gamediceservice.generatedClasses.tables.FlywaySchemaHistory;
import eu.panic.gamediceservice.generatedClasses.tables.GamesTable;
import eu.panic.gamediceservice.generatedClasses.tables.MessagesTable;
import eu.panic.gamediceservice.generatedClasses.tables.ReplenishmentsTable;
import eu.panic.gamediceservice.generatedClasses.tables.SignInHistoryTable;
import eu.panic.gamediceservice.generatedClasses.tables.UsersTable;
import eu.panic.gamediceservice.generatedClasses.tables.records.FlywaySchemaHistoryRecord;
import eu.panic.gamediceservice.generatedClasses.tables.records.GamesTableRecord;
import eu.panic.gamediceservice.generatedClasses.tables.records.MessagesTableRecord;
import eu.panic.gamediceservice.generatedClasses.tables.records.ReplenishmentsTableRecord;
import eu.panic.gamediceservice.generatedClasses.tables.records.SignInHistoryTableRecord;
import eu.panic.gamediceservice.generatedClasses.tables.records.UsersTableRecord;

import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in
 * public.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<FlywaySchemaHistoryRecord> FLYWAY_SCHEMA_HISTORY_PK = Internal.createUniqueKey(FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY, DSL.name("flyway_schema_history_pk"), new TableField[] { FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY.INSTALLED_RANK }, true);
    public static final UniqueKey<GamesTableRecord> GAMES_TABLE_PKEY = Internal.createUniqueKey(GamesTable.GAMES_TABLE, DSL.name("games_table_pkey"), new TableField[] { GamesTable.GAMES_TABLE.ID }, true);
    public static final UniqueKey<MessagesTableRecord> MESSAGES_TABLE_PKEY = Internal.createUniqueKey(MessagesTable.MESSAGES_TABLE, DSL.name("messages_table_pkey"), new TableField[] { MessagesTable.MESSAGES_TABLE.ID }, true);
    public static final UniqueKey<ReplenishmentsTableRecord> REPLENISHMENTS_TABLE_PKEY = Internal.createUniqueKey(ReplenishmentsTable.REPLENISHMENTS_TABLE, DSL.name("replenishments_table_pkey"), new TableField[] { ReplenishmentsTable.REPLENISHMENTS_TABLE.ID }, true);
    public static final UniqueKey<SignInHistoryTableRecord> SIGN_IN_HISTORY_TABLE_PKEY = Internal.createUniqueKey(SignInHistoryTable.SIGN_IN_HISTORY_TABLE, DSL.name("sign_in_history_table_pkey"), new TableField[] { SignInHistoryTable.SIGN_IN_HISTORY_TABLE.ID }, true);
    public static final UniqueKey<UsersTableRecord> USERS_TABLE_PKEY = Internal.createUniqueKey(UsersTable.USERS_TABLE, DSL.name("users_table_pkey"), new TableField[] { UsersTable.USERS_TABLE.ID }, true);
}
