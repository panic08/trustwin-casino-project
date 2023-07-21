/*
 * This file is generated by jOOQ.
 */
package eu.panic.managementgameservice.generatedClasses.tables;


import eu.panic.managementgameservice.generatedClasses.Keys;
import eu.panic.managementgameservice.generatedClasses.Public;
import eu.panic.managementgameservice.generatedClasses.tables.records.WithdrawalsTableRecord;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.jooq.Check;
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
import org.jooq.impl.Internal;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class WithdrawalsTable extends TableImpl<WithdrawalsTableRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.withdrawals_table</code>
     */
    public static final WithdrawalsTable WITHDRAWALS_TABLE = new WithdrawalsTable();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<WithdrawalsTableRecord> getRecordType() {
        return WithdrawalsTableRecord.class;
    }

    /**
     * The column <code>public.withdrawals_table.id</code>.
     */
    public final TableField<WithdrawalsTableRecord, Long> ID = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.withdrawals_table.bet</code>.
     */
    public final TableField<WithdrawalsTableRecord, Long> AMOUNT = createField(DSL.name("bet"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.withdrawals_table.method</code>.
     */
    public final TableField<WithdrawalsTableRecord, String> METHOD = createField(DSL.name("method"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.withdrawals_table.status</code>.
     */
    public final TableField<WithdrawalsTableRecord, String> STATUS = createField(DSL.name("status"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.withdrawals_table.timestamp</code>.
     */
    public final TableField<WithdrawalsTableRecord, Long> TIMESTAMP = createField(DSL.name("timestamp"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.withdrawals_table.username</code>.
     */
    public final TableField<WithdrawalsTableRecord, String> USERNAME = createField(DSL.name("username"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.withdrawals_table.wallet_id</code>.
     */
    public final TableField<WithdrawalsTableRecord, String> WALLET_ID = createField(DSL.name("wallet_id"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    private WithdrawalsTable(Name alias, Table<WithdrawalsTableRecord> aliased) {
        this(alias, aliased, null);
    }

    private WithdrawalsTable(Name alias, Table<WithdrawalsTableRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.withdrawals_table</code> table reference
     */
    public WithdrawalsTable(String alias) {
        this(DSL.name(alias), WITHDRAWALS_TABLE);
    }

    /**
     * Create an aliased <code>public.withdrawals_table</code> table reference
     */
    public WithdrawalsTable(Name alias) {
        this(alias, WITHDRAWALS_TABLE);
    }

    /**
     * Create a <code>public.withdrawals_table</code> table reference
     */
    public WithdrawalsTable() {
        this(DSL.name("withdrawals_table"), null);
    }

    public <O extends Record> WithdrawalsTable(Table<O> child, ForeignKey<O, WithdrawalsTableRecord> key) {
        super(child, key, WITHDRAWALS_TABLE);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public Identity<WithdrawalsTableRecord, Long> getIdentity() {
        return (Identity<WithdrawalsTableRecord, Long>) super.getIdentity();
    }

    @Override
    public UniqueKey<WithdrawalsTableRecord> getPrimaryKey() {
        return Keys.WITHDRAWALS_TABLE_PKEY;
    }

    @Override
    public List<Check<WithdrawalsTableRecord>> getChecks() {
        return Arrays.asList(
            Internal.createCheck(this, DSL.name("withdrawals_table_method_check"), "(((method)::text = ANY ((ARRAY['BTC'::character varying, 'LTC'::character varying, 'ETH'::character varying])::text[])))", true),
            Internal.createCheck(this, DSL.name("withdrawals_table_status_check"), "(((status)::text = ANY ((ARRAY['AWAIT'::character varying, 'CANCELED'::character varying, 'SUCCESSFUL'::character varying])::text[])))", true)
        );
    }

    @Override
    public WithdrawalsTable as(String alias) {
        return new WithdrawalsTable(DSL.name(alias), this);
    }

    @Override
    public WithdrawalsTable as(Name alias) {
        return new WithdrawalsTable(alias, this);
    }

    @Override
    public WithdrawalsTable as(Table<?> alias) {
        return new WithdrawalsTable(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public WithdrawalsTable rename(String name) {
        return new WithdrawalsTable(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public WithdrawalsTable rename(Name name) {
        return new WithdrawalsTable(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public WithdrawalsTable rename(Table<?> name) {
        return new WithdrawalsTable(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row7 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row7<Long, Long, String, String, Long, String, String> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function7<? super Long, ? super Long, ? super String, ? super String, ? super Long, ? super String, ? super String, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function7<? super Long, ? super Long, ? super String, ? super String, ? super Long, ? super String, ? super String, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
