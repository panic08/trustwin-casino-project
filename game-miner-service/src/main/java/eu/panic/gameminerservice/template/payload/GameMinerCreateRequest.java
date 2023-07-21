package eu.panic.gameminerservice.template.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameMinerCreateRequest {
    private long bet;
    private int minesCount;
}
