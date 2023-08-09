/*
 * This file is generated by jOOQ.
 */
package eu.panic.gameminerservice.generatedClasses.tables.records;


import eu.panic.gameminerservice.generatedClasses.tables.NotificationsTable;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class NotificationsTableRecord extends UpdatableRecordImpl<NotificationsTableRecord> implements Record5<Long, Boolean, String, Long, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.notifications_table.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.notifications_table.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.notifications_table.is_watched</code>.
     */
    public void setIsWatched(Boolean value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.notifications_table.is_watched</code>.
     */
    public Boolean getIsWatched() {
        return (Boolean) get(1);
    }

    /**
     * Setter for <code>public.notifications_table.message</code>.
     */
    public void setMessage(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.notifications_table.message</code>.
     */
    public String getMessage() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.notifications_table.timestamp</code>.
     */
    public void setTimestamp(Long value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.notifications_table.timestamp</code>.
     */
    public Long getTimestamp() {
        return (Long) get(3);
    }

    /**
     * Setter for <code>public.notifications_table.username</code>.
     */
    public void setUsername(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.notifications_table.username</code>.
     */
    public String getUsername() {
        return (String) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row5<Long, Boolean, String, Long, String> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    @Override
    public Row5<Long, Boolean, String, Long, String> valuesRow() {
        return (Row5) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return NotificationsTable.NOTIFICATIONS_TABLE.ID;
    }

    @Override
    public Field<Boolean> field2() {
        return NotificationsTable.NOTIFICATIONS_TABLE.IS_WATCHED;
    }

    @Override
    public Field<String> field3() {
        return NotificationsTable.NOTIFICATIONS_TABLE.MESSAGE;
    }

    @Override
    public Field<Long> field4() {
        return NotificationsTable.NOTIFICATIONS_TABLE.TIMESTAMP;
    }

    @Override
    public Field<String> field5() {
        return NotificationsTable.NOTIFICATIONS_TABLE.USERNAME;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public Boolean component2() {
        return getIsWatched();
    }

    @Override
    public String component3() {
        return getMessage();
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
    public Long value1() {
        return getId();
    }

    @Override
    public Boolean value2() {
        return getIsWatched();
    }

    @Override
    public String value3() {
        return getMessage();
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
    public NotificationsTableRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public NotificationsTableRecord value2(Boolean value) {
        setIsWatched(value);
        return this;
    }

    @Override
    public NotificationsTableRecord value3(String value) {
        setMessage(value);
        return this;
    }

    @Override
    public NotificationsTableRecord value4(Long value) {
        setTimestamp(value);
        return this;
    }

    @Override
    public NotificationsTableRecord value5(String value) {
        setUsername(value);
        return this;
    }

    @Override
    public NotificationsTableRecord values(Long value1, Boolean value2, String value3, Long value4, String value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached NotificationsTableRecord
     */
    public NotificationsTableRecord() {
        super(NotificationsTable.NOTIFICATIONS_TABLE);
    }

    /**
     * Create a detached, initialised NotificationsTableRecord
     */
    public NotificationsTableRecord(Long id, Boolean isWatched, String message, Long timestamp, String username) {
        super(NotificationsTable.NOTIFICATIONS_TABLE);

        setId(id);
        setIsWatched(isWatched);
        setMessage(message);
        setTimestamp(timestamp);
        setUsername(username);
        resetChangedOnNotNull();
    }
}
