package eu.panic.gameminerservice.template.payload;

import eu.panic.gameminerservice.template.enums.GameState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameMinerCreateRequest {
    private long amount;
    private int minesCount;
}
