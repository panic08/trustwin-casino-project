/*
 * This file is generated by jOOQ.
 */
package eu.panic.gamecrashservice.generatedClasses.tables.records;


import eu.panic.gamecrashservice.generatedClasses.tables.SignInHistoryTable;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record9;
import org.jooq.Row9;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SignInHistoryTableRecord extends UpdatableRecordImpl<SignInHistoryTableRecord> implements Record9<Long, String, String, String, String, String, String, Long, String> {

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
     * Setter for <code>public.sign_in_history_table.device_name</code>.
     */
    public void setDeviceName(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.sign_in_history_table.device_name</code>.
     */
    public String getDeviceName() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.sign_in_history_table.device_type</code>.
     */
    public void setDeviceType(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.sign_in_history_table.device_type</code>.
     */
    public String getDeviceType() {
        return (String) get(4);
    }

    /**
     * Setter for <code>public.sign_in_history_table.operating_system</code>.
     */
    public void setOperatingSystem(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.sign_in_history_table.operating_system</code>.
     */
    public String getOperatingSystem() {
        return (String) get(5);
    }

    /**
     * Setter for <code>public.sign_in_history_table.ip_address</code>.
     */
    public void setIpAddress(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.sign_in_history_table.ip_address</code>.
     */
    public String getIpAddress() {
        return (String) get(6);
    }

    /**
     * Setter for <code>public.sign_in_history_table.timestamp</code>.
     */
    public void setTimestamp(Long value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.sign_in_history_table.timestamp</code>.
     */
    public Long getTimestamp() {
        return (Long) get(7);
    }

    /**
     * Setter for <code>public.sign_in_history_table.username</code>.
     */
    public void setUsername(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>public.sign_in_history_table.username</code>.
     */
    public String getUsername() {
        return (String) get(8);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record9 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row9<Long, String, String, String, String, String, String, Long, String> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    @Override
    public Row9<Long, String, String, String, String, String, String, Long, String> valuesRow() {
        return (Row9) super.valuesRow();
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
        return SignInHistoryTable.SIGN_IN_HISTORY_TABLE.DEVICE_NAME;
    }

    @Override
    public Field<String> field5() {
        return SignInHistoryTable.SIGN_IN_HISTORY_TABLE.DEVICE_TYPE;
    }

    @Override
    public Field<String> field6() {
        return SignInHistoryTable.SIGN_IN_HISTORY_TABLE.OPERATING_SYSTEM;
    }

    @Override
    public Field<String> field7() {
        return SignInHistoryTable.SIGN_IN_HISTORY_TABLE.IP_ADDRESS;
    }

    @Override
    public Field<Long> field8() {
        return SignInHistoryTable.SIGN_IN_HISTORY_TABLE.TIMESTAMP;
    }

    @Override
    public Field<String> field9() {
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
        return getDeviceName();
    }

    @Override
    public String component5() {
        return getDeviceType();
    }

    @Override
    public String component6() {
        return getOperatingSystem();
    }

    @Override
    public String component7() {
        return getIpAddress();
    }

    @Override
    public Long component8() {
        return getTimestamp();
    }

    @Override
    public String component9() {
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
        return getDeviceName();
    }

    @Override
    public String value5() {
        return getDeviceType();
    }

    @Override
    public String value6() {
        return getOperatingSystem();
    }

    @Override
    public String value7() {
        return getIpAddress();
    }

    @Override
    public Long value8() {
        return getTimestamp();
    }

    @Override
    public String value9() {
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
        setDeviceName(value);
        return this;
    }

    @Override
    public SignInHistoryTableRecord value5(String value) {
        setDeviceType(value);
        return this;
    }

    @Override
    public SignInHistoryTableRecord value6(String value) {
        setOperatingSystem(value);
        return this;
    }

    @Override
    public SignInHistoryTableRecord value7(String value) {
        setIpAddress(value);
        return this;
    }

    @Override
    public SignInHistoryTableRecord value8(Long value) {
        setTimestamp(value);
        return this;
    }

    @Override
    public SignInHistoryTableRecord value9(String value) {
        setUsername(value);
        return this;
    }

    @Override
    public SignInHistoryTableRecord values(Long value1, String value2, String value3, String value4, String value5, String value6, String value7, Long value8, String value9) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
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
    public SignInHistoryTableRecord(Long id, String browserName, String browserVersion, String deviceName, String deviceType, String operatingSystem, String ipAddress, Long timestamp, String username) {
        super(SignInHistoryTable.SIGN_IN_HISTORY_TABLE);

        setId(id);
        setBrowserName(browserName);
        setBrowserVersion(browserVersion);
        setDeviceName(deviceName);
        setDeviceType(deviceType);
        setOperatingSystem(operatingSystem);
        setIpAddress(ipAddress);
        setTimestamp(timestamp);
        setUsername(username);
        resetChangedOnNotNull();
    }
}
