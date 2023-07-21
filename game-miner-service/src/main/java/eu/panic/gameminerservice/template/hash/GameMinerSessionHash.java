package eu.panic.gameminerservice.template.hash;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.List;

@RedisHash("miner_session_hash")
@Getter
@Setter
public class GameMinerSessionHash {
    @Id
    @Indexed
    @JsonIgnore
    private String username;
    private long bet;
    private long win;
    private double coefficient;
    private List<Integer> picked;
    @JsonIgnore
    private List<Integer> notPicked;
    @JsonIgnore
    private List<Integer> mines;
    @JsonIgnore
    private String salt;
    @JsonIgnore
    private long timestamp;
}
