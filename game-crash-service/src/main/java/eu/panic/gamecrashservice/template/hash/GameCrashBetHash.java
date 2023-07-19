package eu.panic.gamecrashservice.template.hash;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("crash_bets_hash")
@Data
public class GameCrashBetHash {
    @Id
    @Indexed
    private String username;
    private Long bet;
    @Indexed
    private Boolean isTaken;
    private Double coefficient;
    private Long win;
    private Long timestamp;
}
