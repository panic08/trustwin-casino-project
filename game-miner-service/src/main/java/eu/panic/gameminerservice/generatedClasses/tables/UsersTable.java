/*
 * This file is generated by jOOQ.
 */
package eu.panic.gameminerservice.generatedClasses.tables;


import eu.panic.gameminerservice.generatedClasses.Keys;
import eu.panic.gameminerservice.generatedClasses.Public;
import eu.panic.gameminerservice.generatedClasses.tables.records.UsersTableRecord;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.jooq.Check;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function22;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row22;
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
public class UsersTable extends TableImpl<UsersTableRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.users_table</code>
     */
    public static final UsersTable USERS_TABLE = new UsersTable();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UsersTableRecord> getRecordType() {
        return UsersTableRecord.class;
    }

    /**
     * The column <code>public.users_table.id</code>.
     */
    public final TableField<UsersTableRecord, Long> ID = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.users_table.balance</code>.
     */
    public final TableField<UsersTableRecord, Long> BALANCE = createField(DSL.name("balance"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.users_table.authorize_type</code>.
     */
    public final TableField<UsersTableRecord, String> AUTHORIZE_TYPE = createField(DSL.name("authorize_type"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.users_table.client_seed</code>.
     */
    public final TableField<UsersTableRecord, String> CLIENT_SEED = createField(DSL.name("client_seed"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.users_table.rank</code>.
     */
    public final TableField<UsersTableRecord, String> RANK = createField(DSL.name("rank"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.users_table.server_seed</code>.
     */
    public final TableField<UsersTableRecord, String> SERVER_SEED = createField(DSL.name("server_seed"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.users_table.email</code>.
     */
    public final TableField<UsersTableRecord, String> EMAIL = createField(DSL.name("email"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.users_table.ip_address</code>.
     */
    public final TableField<UsersTableRecord, String> IP_ADDRESS = createField(DSL.name("ip_address"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.users_table.is_account_non_locked</code>.
     */
    public final TableField<UsersTableRecord, Boolean> IS_ACCOUNT_NON_LOCKED = createField(DSL.name("is_account_non_locked"), SQLDataType.BOOLEAN.nullable(false), this, "");

    /**
     * The column <code>public.users_table.is_multi_account</code>.
     */
    public final TableField<UsersTableRecord, Boolean> IS_MULTI_ACCOUNT = createField(DSL.name("is_multi_account"), SQLDataType.BOOLEAN.nullable(false), this, "");

    /**
     * The column <code>public.users_table.password</code>.
     */
    public final TableField<UsersTableRecord, String> PASSWORD = createField(DSL.name("password"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.users_table.birthday</code>.
     */
    public final TableField<UsersTableRecord, String> BIRTHDAY = createField(DSL.name("birthday"), SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>public.users_table.gender</code>.
     */
    public final TableField<UsersTableRecord, String> GENDER = createField(DSL.name("gender"), SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>public.users_table.nickname</code>.
     */
    public final TableField<UsersTableRecord, String> NICKNAME = createField(DSL.name("nickname"), SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>public.users_table.earned</code>.
     */
    public final TableField<UsersTableRecord, Long> EARNED = createField(DSL.name("earned"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.users_table.invited</code>.
     */
    public final TableField<UsersTableRecord, Long> INVITED = createField(DSL.name("invited"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.users_table.invited_by</code>.
     */
    public final TableField<UsersTableRecord, String> INVITED_BY = createField(DSL.name("invited_by"), SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>public.users_table.level</code>.
     */
    public final TableField<UsersTableRecord, Integer> LEVEL = createField(DSL.name("level"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.users_table.ref_link</code>.
     */
    public final TableField<UsersTableRecord, String> REF_LINK = createField(DSL.name("ref_link"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.users_table.registered_at</code>.
     */
    public final TableField<UsersTableRecord, Long> REGISTERED_AT = createField(DSL.name("registered_at"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.users_table.role</code>.
     */
    public final TableField<UsersTableRecord, String> ROLE = createField(DSL.name("role"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.users_table.username</code>.
     */
    public final TableField<UsersTableRecord, String> USERNAME = createField(DSL.name("username"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    private UsersTable(Name alias, Table<UsersTableRecord> aliased) {
        this(alias, aliased, null);
    }

    private UsersTable(Name alias, Table<UsersTableRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.users_table</code> table reference
     */
    public UsersTable(String alias) {
        this(DSL.name(alias), USERS_TABLE);
    }

    /**
     * Create an aliased <code>public.users_table</code> table reference
     */
    public UsersTable(Name alias) {
        this(alias, USERS_TABLE);
    }

    /**
     * Create a <code>public.users_table</code> table reference
     */
    public UsersTable() {
        this(DSL.name("users_table"), null);
    }

    public <O extends Record> UsersTable(Table<O> child, ForeignKey<O, UsersTableRecord> key) {
        super(child, key, USERS_TABLE);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public Identity<UsersTableRecord, Long> getIdentity() {
        return (Identity<UsersTableRecord, Long>) super.getIdentity();
    }

    @Override
    public UniqueKey<UsersTableRecord> getPrimaryKey() {
        return Keys.USERS_TABLE_PKEY;
    }

    @Override
    public List<Check<UsersTableRecord>> getChecks() {
        return Arrays.asList(
            Internal.createCheck(this, DSL.name("users_table_authorize_type_check"), "(((authorize_type)::text = ANY ((ARRAY['DEFAULT'::character varying, 'GOOGLE'::character varying])::text[])))", true),
            Internal.createCheck(this, DSL.name("users_table_gender_check"), "(((gender)::text = ANY ((ARRAY['MALE'::character varying, 'FEMALE'::character varying, 'OTHER'::character varying])::text[])))", true),
            Internal.createCheck(this, DSL.name("users_table_rank_check"), "(((rank)::text = ANY ((ARRAY['NEWBIE'::character varying, 'BEGINNER'::character varying, 'AMATEUR'::character varying, 'GAMBLING'::character varying, 'PRO'::character varying, 'SHARK'::character varying, 'MASTER'::character varying, 'GRANDMASTER'::character varying, 'CASH_MACHINE'::character varying, 'DRAKE'::character varying, 'DIAMOND'::character varying, 'SUPERIOR'::character varying, 'LEGEND'::character varying, 'INTERNATIONAL'::character varying, 'IMMORTAL'::character varying, 'GODLIKE'::character varying, 'DIVINE'::character varying])::text[])))", true),
            Internal.createCheck(this, DSL.name("users_table_role_check"), "(((role)::text = ANY ((ARRAY['DEFAULT'::character varying, 'MODERATOR'::character varying, 'ADMIN'::character varying])::text[])))", true)
        );
    }

    @Override
    public UsersTable as(String alias) {
        return new UsersTable(DSL.name(alias), this);
    }

    @Override
    public UsersTable as(Name alias) {
        return new UsersTable(alias, this);
    }

    @Override
    public UsersTable as(Table<?> alias) {
        return new UsersTable(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public UsersTable rename(String name) {
        return new UsersTable(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public UsersTable rename(Name name) {
        return new UsersTable(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public UsersTable rename(Table<?> name) {
        return new UsersTable(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row22 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row22<Long, Long, String, String, String, String, String, String, Boolean, Boolean, String, String, String, String, Long, Long, String, Integer, String, Long, String, String> fieldsRow() {
        return (Row22) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function22<? super Long, ? super Long, ? super String, ? super String, ? super String, ? super String, ? super String, ? super String, ? super Boolean, ? super Boolean, ? super String, ? super String, ? super String, ? super String, ? super Long, ? super Long, ? super String, ? super Integer, ? super String, ? super Long, ? super String, ? super String, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function22<? super Long, ? super Long, ? super String, ? super String, ? super String, ? super String, ? super String, ? super String, ? super Boolean, ? super Boolean, ? super String, ? super String, ? super String, ? super String, ? super Long, ? super Long, ? super String, ? super Integer, ? super String, ? super Long, ? super String, ? super String, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
