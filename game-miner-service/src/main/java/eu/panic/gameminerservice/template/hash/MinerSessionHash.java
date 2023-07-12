package eu.panic.gameminerservice.template.hash;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.List;

@RedisHash("miner_sessions_hash")
@Getter
@Setter
public class MinerSessionHash {
    @Id
    @Indexed
    private String username;
    private long bet;
    private long win;
    private double coefficient;
    private List<Integer> picked;
    @JsonIgnore
    private List<Integer> notPicked;
    @JsonIgnore
    private List<Integer> mines;
    private String salt;
    private Long timestamp;
}
