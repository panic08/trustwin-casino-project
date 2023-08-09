/*
 * This file is generated by jOOQ.
 */
package eu.panic.bonusservice.generatedClasses.tables;


import eu.panic.bonusservice.generatedClasses.Keys;
import eu.panic.bonusservice.generatedClasses.Public;
import eu.panic.bonusservice.generatedClasses.tables.records.GamesTableRecord;

import java.util.function.Function;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function11;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row11;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class GamesTable extends TableImpl<GamesTableRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.games_table</code>
     */
    public static final GamesTable GAMES_TABLE = new GamesTable();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<GamesTableRecord> getRecordType() {
        return GamesTableRecord.class;
    }

    /**
     * The column <code>public.games_table.id</code>.
     */
    public final TableField<GamesTableRecord, Long> ID = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.games_table.game_type</code>.
     */
    public final TableField<GamesTableRecord, String> GAME_TYPE = createField(DSL.name("game_type"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.games_table.username</code>.
     */
    public final TableField<GamesTableRecord, String> USERNAME = createField(DSL.name("username"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.games_table.nickname</code>.
     */
    public final TableField<GamesTableRecord, String> NICKNAME = createField(DSL.name("nickname"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.games_table.win</code>.
     */
    public final TableField<GamesTableRecord, Long> BET = createField(DSL.name("win"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.games_table.win</code>.
     */
    public final TableField<GamesTableRecord, Long> WIN = createField(DSL.name("win"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.games_table.coefficient</code>.
     */
    public final TableField<GamesTableRecord, Double> COEFFICIENT = createField(DSL.name("coefficient"), SQLDataType.DOUBLE.nullable(false), this, "");

    /**
     * The column <code>public.games_table.client_seed</code>.
     */
    public final TableField<GamesTableRecord, String> CLIENT_SEED = createField(DSL.name("client_seed"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.games_table.server_seed</code>.
     */
    public final TableField<GamesTableRecord, String> SERVER_SEED = createField(DSL.name("server_seed"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.games_table.salt</code>.
     */
    public final TableField<GamesTableRecord, String> SALT = createField(DSL.name("salt"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.games_table.timestamp</code>.
     */
    public final TableField<GamesTableRecord, Long> TIMESTAMP = createField(DSL.name("timestamp"), SQLDataType.BIGINT.nullable(false), this, "");

    private GamesTable(Name alias, Table<GamesTableRecord> aliased) {
        this(alias, aliased, null);
    }

    private GamesTable(Name alias, Table<GamesTableRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.games_table</code> table reference
     */
    public GamesTable(String alias) {
        this(DSL.name(alias), GAMES_TABLE);
    }

    /**
     * Create an aliased <code>public.games_table</code> table reference
     */
    public GamesTable(Name alias) {
        this(alias, GAMES_TABLE);
    }

    /**
     * Create a <code>public.games_table</code> table reference
     */
    public GamesTable() {
        this(DSL.name("games_table"), null);
    }

    public <O extends Record> GamesTable(Table<O> child, ForeignKey<O, GamesTableRecord> key) {
        super(child, key, GAMES_TABLE);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public Identity<GamesTableRecord, Long> getIdentity() {
        return (Identity<GamesTableRecord, Long>) super.getIdentity();
    }

    @Override
    public UniqueKey<GamesTableRecord> getPrimaryKey() {
        return Keys.GAMES_TABLE_PKEY;
    }

    @Override
    public GamesTable as(String alias) {
        return new GamesTable(DSL.name(alias), this);
    }

    @Override
    public GamesTable as(Name alias) {
        return new GamesTable(alias, this);
    }

    @Override
    public GamesTable as(Table<?> alias) {
        return new GamesTable(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public GamesTable rename(String name) {
        return new GamesTable(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public GamesTable rename(Name name) {
        return new GamesTable(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public GamesTable rename(Table<?> name) {
        return new GamesTable(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row11 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row11<Long, String, String, String, Long, Long, Double, String, String, String, Long> fieldsRow() {
        return (Row11) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function11<? super Long, ? super String, ? super String, ? super String, ? super Long, ? super Long, ? super Double, ? super String, ? super String, ? super String, ? super Long, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function11<? super Long, ? super String, ? super String, ? super String, ? super Long, ? super Long, ? super Double, ? super String, ? super String, ? super String, ? super Long, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
