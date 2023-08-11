/*
 * This file is generated by jOOQ.
 */
package eu.panic.gameovergoservice.generatedClasses.tables.records;


import eu.panic.gameovergoservice.generatedClasses.tables.SignInHistoryTable;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SignInHistoryTableRecord extends UpdatableRecordImpl<SignInHistoryTableRecord> implements Record6<Long, String, String, String, Long, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.sign_in_history_table.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.sign_in_history_table.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.sign_in_history_table.browser_name</code>.
     */
    public void setBrowserName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.sign_in_history_table.browser_name</code>.
     */
    public String getBrowserName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.sign_in_history_table.browser_version</code>.
     */
    public void setBrowserVersion(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.sign_in_history_table.browser_version</code>.
     */
    public String getBrowserVersion() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.sign_in_history_table.ip_address</code>.
     */
    public void setIpAddress(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.sign_in_history_table.ip_address</code>.
     */
    public String getIpAddress() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.sign_in_history_table.timestamp</code>.
     */
    public void setTimestamp(Long value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.sign_in_history_table.timestamp</code>.
     */
    public Long getTimestamp() {
        return (Long) get(4);
    }

    /**
     * Setter for <code>public.sign_in_history_table.username</code>.
     */
    public void setUsername(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.sign_in_history_table.username</code>.
     */
    public String getUsername() {
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
    public Row6<Long, String, String, String, Long, String> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    @Override
    public Row6<Long, String, String, String, Long, String> valuesRow() {
        return (Row6) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return SignInHistoryTable.SIGN_IN_HISTORY_TABLE.ID;
    }

    @Override
    public Field<String> field2() {
        return SignInHistoryTable.SIGN_IN_HISTORY_TABLE.BROWSER_NAME;
    }

    @Override
    public Field<String> field3() {
        return SignInHistoryTable.SIGN_IN_HISTORY_TABLE.BROWSER_VERSION;
    }

    @Override
    public Field<String> field4() {
        return SignInHistoryTable.SIGN_IN_HISTORY_TABLE.IP_ADDRESS;
    }

    @Override
    public Field<Long> field5() {
        return SignInHistoryTable.SIGN_IN_HISTORY_TABLE.TIMESTAMP;
    }

    @Override
    public Field<String> field6() {
        return SignInHistoryTable.SIGN_IN_HISTORY_TABLE.USERNAME;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getBrowserName();
    }

    @Override
    public String component3() {
        return getBrowserVersion();
    }

    @Override
    public String component4() {
        return getIpAddress();
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
    public Long value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getBrowserName();
    }

    @Override
    public String value3() {
        return getBrowserVersion();
    }

    @Override
    public String value4() {
        return getIpAddress();
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
    public SignInHistoryTableRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public SignInHistoryTableRecord value2(String value) {
        setBrowserName(value);
        return this;
    }

    @Override
    public SignInHistoryTableRecord value3(String value) {
        setBrowserVersion(value);
        return this;
    }

    @Override
    public SignInHistoryTableRecord value4(String value) {
        setIpAddress(value);
        return this;
    }

    @Override
    public SignInHistoryTableRecord value5(Long value) {
        setTimestamp(value);
        return this;
    }

    @Override
    public SignInHistoryTableRecord value6(String value) {
        setUsername(value);
        return this;
    }

    @Override
    public SignInHistoryTableRecord values(Long value1, String value2, String value3, String value4, Long value5, String value6) {
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
     * Create a detached SignInHistoryTableRecord
     */
    public SignInHistoryTableRecord() {
        super(SignInHistoryTable.SIGN_IN_HISTORY_TABLE);
    }

    /**
     * Create a detached, initialised SignInHistoryTableRecord
     */
    public SignInHistoryTableRecord(Long id, String browserName, String browserVersion, String ipAddress, Long timestamp, String username) {
        super(SignInHistoryTable.SIGN_IN_HISTORY_TABLE);

        setId(id);
        setBrowserName(browserName);
        setBrowserVersion(browserVersion);
        setIpAddress(ipAddress);
        setTimestamp(timestamp);
        setUsername(username);
        resetChangedOnNotNull();
    }
}
