/*
 * This file is generated by jOOQ.
 */
package eu.panic.gameovergoservice.generatedClasses.tables.records;


import eu.panic.gameovergoservice.generatedClasses.tables.GamesTable;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record11;
import org.jooq.Row11;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class GamesTableRecord extends UpdatableRecordImpl<GamesTableRecord> implements Record11<Long, String, String, String, Long, Long, Double, String, String, String, Long> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.games_table.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.games_table.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.games_table.game_type</code>.
     */
    public void setGameType(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.games_table.game_type</code>.
     */
    public String getGameType() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.games_table.username</code>.
     */
    public void setUsername(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.games_table.username</code>.
     */
    public String getUsername() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.games_table.nickname</code>.
     */
    public void setNickname(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.games_table.nickname</code>.
     */
    public String getNickname() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.games_table.bet</code>.
     */
    public void setBet(Long value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.games_table.bet</code>.
     */
    public Long getBet() {
        return (Long) get(4);
    }

    /**
     * Setter for <code>public.games_table.win</code>.
     */
    public void setWin(Long value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.games_table.win</code>.
     */
    public Long getWin() {
        return (Long) get(5);
    }

    /**
     * Setter for <code>public.games_table.coefficient</code>.
     */
    public void setCoefficient(Double value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.games_table.coefficient</code>.
     */
    public Double getCoefficient() {
        return (Double) get(6);
    }

    /**
     * Setter for <code>public.games_table.client_seed</code>.
     */
    public void setClientSeed(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.games_table.client_seed</code>.
     */
    public String getClientSeed() {
        return (String) get(7);
    }

    /**
     * Setter for <code>public.games_table.server_seed</code>.
     */
    public void setServerSeed(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>public.games_table.server_seed</code>.
     */
    public String getServerSeed() {
        return (String) get(8);
    }

    /**
     * Setter for <code>public.games_table.salt</code>.
     */
    public void setSalt(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>public.games_table.salt</code>.
     */
    public String getSalt() {
        return (String) get(9);
    }

    /**
     * Setter for <code>public.games_table.timestamp</code>.
     */
    public void setTimestamp(Long value) {
        set(10, value);
    }

    /**
     * Getter for <code>public.games_table.timestamp</code>.
     */
    public Long getTimestamp() {
        return (Long) get(10);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record11 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row11<Long, String, String, String, Long, Long, Double, String, String, String, Long> fieldsRow() {
        return (Row11) super.fieldsRow();
    }

    @Override
    public Row11<Long, String, String, String, Long, Long, Double, String, String, String, Long> valuesRow() {
        return (Row11) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return GamesTable.GAMES_TABLE.ID;
    }

    @Override
    public Field<String> field2() {
        return GamesTable.GAMES_TABLE.GAME_TYPE;
    }

    @Override
    public Field<String> field3() {
        return GamesTable.GAMES_TABLE.USERNAME;
    }

    @Override
    public Field<String> field4() {
        return GamesTable.GAMES_TABLE.NICKNAME;
    }

    @Override
    public Field<Long> field5() {
        return GamesTable.GAMES_TABLE.BET;
    }

    @Override
    public Field<Long> field6() {
        return GamesTable.GAMES_TABLE.WIN;
    }

    @Override
    public Field<Double> field7() {
        return GamesTable.GAMES_TABLE.COEFFICIENT;
    }

    @Override
    public Field<String> field8() {
        return GamesTable.GAMES_TABLE.CLIENT_SEED;
    }

    @Override
    public Field<String> field9() {
        return GamesTable.GAMES_TABLE.SERVER_SEED;
    }

    @Override
    public Field<String> field10() {
        return GamesTable.GAMES_TABLE.SALT;
    }

    @Override
    public Field<Long> field11() {
        return GamesTable.GAMES_TABLE.TIMESTAMP;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getGameType();
    }

    @Override
    public String component3() {
        return getUsername();
    }

    @Override
    public String component4() {
        return getNickname();
    }

    @Override
    public Long component5() {
        return getBet();
    }

    @Override
    public Long component6() {
        return getWin();
    }

    @Override
    public Double component7() {
        return getCoefficient();
    }

    @Override
    public String component8() {
        return getClientSeed();
    }

    @Override
    public String component9() {
        return getServerSeed();
    }

    @Override
    public String component10() {
        return getSalt();
    }

    @Override
    public Long component11() {
        return getTimestamp();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getGameType();
    }

    @Override
    public String value3() {
        return getUsername();
    }

    @Override
    public String value4() {
        return getNickname();
    }

    @Override
    public Long value5() {
        return getBet();
    }

    @Override
    public Long value6() {
        return getWin();
    }

    @Override
    public Double value7() {
        return getCoefficient();
    }

    @Override
    public String value8() {
        return getClientSeed();
    }

    @Override
    public String value9() {
        return getServerSeed();
    }

    @Override
    public String value10() {
        return getSalt();
    }

    @Override
    public Long value11() {
        return getTimestamp();
    }

    @Override
    public GamesTableRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public GamesTableRecord value2(String value) {
        setGameType(value);
        return this;
    }

    @Override
    public GamesTableRecord value3(String value) {
        setUsername(value);
        return this;
    }

    @Override
    public GamesTableRecord value4(String value) {
        setNickname(value);
        return this;
    }

    @Override
    public GamesTableRecord value5(Long value) {
        setBet(value);
        return this;
    }

    @Override
    public GamesTableRecord value6(Long value) {
        setWin(value);
        return this;
    }

    @Override
    public GamesTableRecord value7(Double value) {
        setCoefficient(value);
        return this;
    }

    @Override
    public GamesTableRecord value8(String value) {
        setClientSeed(value);
        return this;
    }

    @Override
    public GamesTableRecord value9(String value) {
        setServerSeed(value);
        return this;
    }

    @Override
    public GamesTableRecord value10(String value) {
        setSalt(value);
        return this;
    }

    @Override
    public GamesTableRecord value11(Long value) {
        setTimestamp(value);
        return this;
    }

    @Override
    public GamesTableRecord values(Long value1, String value2, String value3, String value4, Long value5, Long value6, Double value7, String value8, String value9, String value10, Long value11) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached GamesTableRecord
     */
    public GamesTableRecord() {
        super(GamesTable.GAMES_TABLE);
    }

    /**
     * Create a detached, initialised GamesTableRecord
     */
    public GamesTableRecord(Long id, String gameType, String username, String nickname, Long bet, Long win, Double coefficient, String clientSeed, String serverSeed, String salt, Long timestamp) {
        super(GamesTable.GAMES_TABLE);

        setId(id);
        setGameType(gameType);
        setUsername(username);
        setNickname(nickname);
        setBet(bet);
        setWin(win);
        setCoefficient(coefficient);
        setClientSeed(clientSeed);
        setServerSeed(serverSeed);
        setSalt(salt);
        setTimestamp(timestamp);
        resetChangedOnNotNull();
    }
}
