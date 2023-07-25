package eu.panic.gamejackpotservice.template.hash;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eu.panic.gamejackpotservice.template.dto.UserDto;
import eu.panic.gamejackpotservice.template.enums.JackpotRoom;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("jackpot_bets_hash")
@Data
public class GameJackpotBetHash {
    @Id
    @Indexed
    @JsonIgnore
    private String id;
    private UserDto user;
    private Long bet;
    @Indexed
    private JackpotRoom room;
    private Long ticketsStarts;
    private Long ticketsEnds;
    private Long timestamp;
}
