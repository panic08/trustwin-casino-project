/*
 * This file is generated by jOOQ.
 */
package eu.panic.gameovergoservice.generatedClasses.tables.records;


import eu.panic.gameovergoservice.generatedClasses.tables.ReplenishmentsTable;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ReplenishmentsTableRecord extends UpdatableRecordImpl<ReplenishmentsTableRecord> implements Record6<Long, Double, String, Long, String, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.replenishments_table.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.replenishments_table.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.replenishments_table.bet</code>.
     */
    public void setAmount(Double value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.replenishments_table.bet</code>.
     */
    public Double getAmount() {
        return (Double) get(1);
    }

    /**
     * Setter for <code>public.replenishments_table.payment_method</code>.
     */
    public void setPaymentMethod(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.replenishments_table.payment_method</code>.
     */
    public String getPaymentMethod() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.replenishments_table.timestamp</code>.
     */
    public void setTimestamp(Long value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.replenishments_table.timestamp</code>.
     */
    public Long getTimestamp() {
        return (Long) get(3);
    }

    /**
     * Setter for <code>public.replenishments_table.username</code>.
     */
    public void setUsername(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.replenishments_table.username</code>.
     */
    public String getUsername() {
        return (String) get(4);
    }

    /**
     * Setter for <code>public.replenishments_table.wallet_id</code>.
     */
    public void setWalletId(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.replenishments_table.wallet_id</code>.
     */
    public String getWalletId() {
        return (String) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row6<Long, Double, String, Long, String, String> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    @Override
    public Row6<Long, Double, String, Long, String, String> valuesRow() {
        return (Row6) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return ReplenishmentsTable.REPLENISHMENTS_TABLE.ID;
    }

    @Override
    public Field<Double> field2() {
        return ReplenishmentsTable.REPLENISHMENTS_TABLE.AMOUNT;
    }

    @Override
    public Field<String> field3() {
        return ReplenishmentsTable.REPLENISHMENTS_TABLE.PAYMENT_METHOD;
    }

    @Override
    public Field<Long> field4() {
        return ReplenishmentsTable.REPLENISHMENTS_TABLE.TIMESTAMP;
    }

    @Override
    public Field<String> field5() {
        return ReplenishmentsTable.REPLENISHMENTS_TABLE.USERNAME;
    }

    @Override
    public Field<String> field6() {
        return ReplenishmentsTable.REPLENISHMENTS_TABLE.WALLET_ID;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public Double component2() {
        return getAmount();
    }

    @Override
    public String component3() {
        return getPaymentMethod();
    }

    @Override
    public Long component4() {
        return getTimestamp();
    }

    @Override
    public String component5() {
        return getUsername();
    }

    @Override
    public String component6() {
        return getWalletId();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public Double value2() {
        return getAmount();
    }

    @Override
    public String value3() {
        return getPaymentMethod();
    }

    @Override
    public Long value4() {
        return getTimestamp();
    }

    @Override
    public String value5() {
        return getUsername();
    }

    @Override
    public String value6() {
        return getWalletId();
    }

    @Override
    public ReplenishmentsTableRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public ReplenishmentsTableRecord value2(Double value) {
        setAmount(value);
        return this;
    }

    @Override
    public ReplenishmentsTableRecord value3(String value) {
        setPaymentMethod(value);
        return this;
    }

    @Override
    public ReplenishmentsTableRecord value4(Long value) {
        setTimestamp(value);
        return this;
    }

    @Override
    public ReplenishmentsTableRecord value5(String value) {
        setUsername(value);
        return this;
    }

    @Override
    public ReplenishmentsTableRecord value6(String value) {
        setWalletId(value);
        return this;
    }

    @Override
    public ReplenishmentsTableRecord values(Long value1, Double value2, String value3, Long value4, String value5, String value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ReplenishmentsTableRecord
     */
    public ReplenishmentsTableRecord() {
        super(ReplenishmentsTable.REPLENISHMENTS_TABLE);
    }

    /**
     * Create a detached, initialised ReplenishmentsTableRecord
     */
    public ReplenishmentsTableRecord(Long id, Double amount, String paymentMethod, Long timestamp, String username, String walletId) {
        super(ReplenishmentsTable.REPLENISHMENTS_TABLE);

        setId(id);
        setAmount(amount);
        setPaymentMethod(paymentMethod);
        setTimestamp(timestamp);
        setUsername(username);
        setWalletId(walletId);
        resetChangedOnNotNull();
    }
}
