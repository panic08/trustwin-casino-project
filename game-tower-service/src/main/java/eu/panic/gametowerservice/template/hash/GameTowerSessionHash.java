package eu.panic.gametowerservice.template.hash;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.List;

@RedisHash("tower_session_hash")
@Getter
@Setter
public class GameTowerSessionHash {
    @Id
    @Indexed
    private String username;
    private int step;
    private long bet;
    private long win;
    private double coefficient;
    private String salt;
    private List<Integer> picked;
    private String mines;
    private long timestamp;
}
