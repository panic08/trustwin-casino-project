/*
 * This file is generated by jOOQ.
 */
package eu.panic.bonusservice.generatedClasses.tables.records;


import eu.panic.bonusservice.generatedClasses.tables.WithdrawalsTable;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class WithdrawalsTableRecord extends UpdatableRecordImpl<WithdrawalsTableRecord> implements Record7<Long, Long, String, String, Long, String, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.withdrawals_table.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.withdrawals_table.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.withdrawals_table.win</code>.
     */
    public void setAmount(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.withdrawals_table.win</code>.
     */
    public Long getAmount() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>public.withdrawals_table.method</code>.
     */
    public void setMethod(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.withdrawals_table.method</code>.
     */
    public String getMethod() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.withdrawals_table.status</code>.
     */
    public void setStatus(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.withdrawals_table.status</code>.
     */
    public String getStatus() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.withdrawals_table.timestamp</code>.
     */
    public void setTimestamp(Long value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.withdrawals_table.timestamp</code>.
     */
    public Long getTimestamp() {
        return (Long) get(4);
    }

    /**
     * Setter for <code>public.withdrawals_table.username</code>.
     */
    public void setUsername(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.withdrawals_table.username</code>.
     */
    public String getUsername() {
        return (String) get(5);
    }

    /**
     * Setter for <code>public.withdrawals_table.wallet_id</code>.
     */
    public void setWalletId(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.withdrawals_table.wallet_id</code>.
     */
    public String getWalletId() {
        return (String) get(6);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record7 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row7<Long, Long, String, String, Long, String, String> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    @Override
    public Row7<Long, Long, String, String, Long, String, String> valuesRow() {
        return (Row7) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return WithdrawalsTable.WITHDRAWALS_TABLE.ID;
    }

    @Override
    public Field<Long> field2() {
        return WithdrawalsTable.WITHDRAWALS_TABLE.AMOUNT;
    }

    @Override
    public Field<String> field3() {
        return WithdrawalsTable.WITHDRAWALS_TABLE.METHOD;
    }

    @Override
    public Field<String> field4() {
        return WithdrawalsTable.WITHDRAWALS_TABLE.STATUS;
    }

    @Override
    public Field<Long> field5() {
        return WithdrawalsTable.WITHDRAWALS_TABLE.TIMESTAMP;
    }

    @Override
    public Field<String> field6() {
        return WithdrawalsTable.WITHDRAWALS_TABLE.USERNAME;
    }

    @Override
    public Field<String> field7() {
        return WithdrawalsTable.WITHDRAWALS_TABLE.WALLET_ID;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public Long component2() {
        return getAmount();
    }

    @Override
    public String component3() {
        return getMethod();
    }

    @Override
    public String component4() {
        return getStatus();
    }

    @Override
    public Long component5() {
        return getTimestamp();
    }

    @Override
    public String component6() {
        return getUsername();
    }

    @Override
    public String component7() {
        return getWalletId();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public Long value2() {
        return getAmount();
    }

    @Override
    public String value3() {
        return getMethod();
    }

    @Override
    public String value4() {
        return getStatus();
    }

    @Override
    public Long value5() {
        return getTimestamp();
    }

    @Override
    public String value6() {
        return getUsername();
    }

    @Override
    public String value7() {
        return getWalletId();
    }

    @Override
    public WithdrawalsTableRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public WithdrawalsTableRecord value2(Long value) {
        setAmount(value);
        return this;
    }

    @Override
    public WithdrawalsTableRecord value3(String value) {
        setMethod(value);
        return this;
    }

    @Override
    public WithdrawalsTableRecord value4(String value) {
        setStatus(value);
        return this;
    }

    @Override
    public WithdrawalsTableRecord value5(Long value) {
        setTimestamp(value);
        return this;
    }

    @Override
    public WithdrawalsTableRecord value6(String value) {
        setUsername(value);
        return this;
    }

    @Override
    public WithdrawalsTableRecord value7(String value) {
        setWalletId(value);
        return this;
    }

    @Override
    public WithdrawalsTableRecord values(Long value1, Long value2, String value3, String value4, Long value5, String value6, String value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached WithdrawalsTableRecord
     */
    public WithdrawalsTableRecord() {
        super(WithdrawalsTable.WITHDRAWALS_TABLE);
    }

    /**
     * Create a detached, initialised WithdrawalsTableRecord
     */
    public WithdrawalsTableRecord(Long id, Long amount, String method, String status, Long timestamp, String username, String walletId) {
        super(WithdrawalsTable.WITHDRAWALS_TABLE);

        setId(id);
        setAmount(amount);
        setMethod(method);
        setStatus(status);
        setTimestamp(timestamp);
        setUsername(username);
        setWalletId(walletId);
        resetChangedOnNotNull();
    }
}
