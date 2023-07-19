package eu.panic.gamecrashservice.template.entity;

import eu.panic.gamecrashservice.template.enums.GameType;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;

@Data
public class Game {
    @Column("id")
    private Long id;
    @Column("game_type")
    private GameType gameType;
    @Column("username")
    private String username;
    @Column("nickname")
    private String nickname;
    @Column("bet")
    private Long bet;
    @Column("win")
    private Long win;
    @Column("coefficient")
    private Double coefficient;
    @Column("client_seed")
    private String clientSeed;
    @Column("server_seed")
    private String serverSeed;
    @Column("salt")
    private String salt;
    @Column("timestamp")
    private Long timestamp;
}