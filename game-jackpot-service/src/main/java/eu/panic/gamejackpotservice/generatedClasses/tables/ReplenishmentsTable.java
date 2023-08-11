/*
 * This file is generated by jOOQ.
 */
package eu.panic.gamejackpotservice.generatedClasses.tables;


import eu.panic.gamejackpotservice.generatedClasses.Keys;
import eu.panic.gamejackpotservice.generatedClasses.Public;
import eu.panic.gamejackpotservice.generatedClasses.tables.records.ReplenishmentsTableRecord;

import java.util.function.Function;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function7;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row7;
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
public class ReplenishmentsTable extends TableImpl<ReplenishmentsTableRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.replenishments_table</code>
     */
    public static final ReplenishmentsTable REPLENISHMENTS_TABLE = new ReplenishmentsTable();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ReplenishmentsTableRecord> getRecordType() {
        return ReplenishmentsTableRecord.class;
    }

    /**
     * The column <code>public.replenishments_table.id</code>.
     */
    public final TableField<ReplenishmentsTableRecord, Long> ID = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.replenishments_table.username</code>.
     */
    public final TableField<ReplenishmentsTableRecord, String> USERNAME = createField(DSL.name("username"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.replenishments_table.wallet_id</code>.
     */
    public final TableField<ReplenishmentsTableRecord, String> WALLET_ID = createField(DSL.name("wallet_id"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.replenishments_table.amount</code>.
     */
    public final TableField<ReplenishmentsTableRecord, Double> AMOUNT = createField(DSL.name("amount"), SQLDataType.DOUBLE.nullable(false), this, "");

    /**
     * The column <code>public.replenishments_table.payment_method</code>.
     */
    public final TableField<ReplenishmentsTableRecord, String> PAYMENT_METHOD = createField(DSL.name("payment_method"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.replenishments_table.timestamp</code>.
     */
    public final TableField<ReplenishmentsTableRecord, Long> TIMESTAMP = createField(DSL.name("timestamp"), SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.replenishments_table.win</code>.
     */
    public final TableField<ReplenishmentsTableRecord, Double> WIN = createField(DSL.name("win"), SQLDataType.DOUBLE.nullable(false), this, "");

    private ReplenishmentsTable(Name alias, Table<ReplenishmentsTableRecord> aliased) {
        this(alias, aliased, null);
    }

    private ReplenishmentsTable(Name alias, Table<ReplenishmentsTableRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.replenishments_table</code> table
     * reference
     */
    public ReplenishmentsTable(String alias) {
        this(DSL.name(alias), REPLENISHMENTS_TABLE);
    }

    /**
     * Create an aliased <code>public.replenishments_table</code> table
     * reference
     */
    public ReplenishmentsTable(Name alias) {
        this(alias, REPLENISHMENTS_TABLE);
    }

    /**
     * Create a <code>public.replenishments_table</code> table reference
     */
    public ReplenishmentsTable() {
        this(DSL.name("replenishments_table"), null);
    }

    public <O extends Record> ReplenishmentsTable(Table<O> child, ForeignKey<O, ReplenishmentsTableRecord> key) {
        super(child, key, REPLENISHMENTS_TABLE);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public Identity<ReplenishmentsTableRecord, Long> getIdentity() {
        return (Identity<ReplenishmentsTableRecord, Long>) super.getIdentity();
    }

    @Override
    public UniqueKey<ReplenishmentsTableRecord> getPrimaryKey() {
        return Keys.REPLENISHMENTS_TABLE_PKEY;
    }

    @Override
    public ReplenishmentsTable as(String alias) {
        return new ReplenishmentsTable(DSL.name(alias), this);
    }

    @Override
    public ReplenishmentsTable as(Name alias) {
        return new ReplenishmentsTable(alias, this);
    }

    @Override
    public ReplenishmentsTable as(Table<?> alias) {
        return new ReplenishmentsTable(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public ReplenishmentsTable rename(String name) {
        return new ReplenishmentsTable(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ReplenishmentsTable rename(Name name) {
        return new ReplenishmentsTable(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public ReplenishmentsTable rename(Table<?> name) {
        return new ReplenishmentsTable(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row7 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row7<Long, String, String, Double, String, Long, Double> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function7<? super Long, ? super String, ? super String, ? super Double, ? super String, ? super Long, ? super Double, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function7<? super Long, ? super String, ? super String, ? super Double, ? super String, ? super Long, ? super Double, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
