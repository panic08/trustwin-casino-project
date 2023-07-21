package eu.panic.gamediceservice.template.payload;

import eu.panic.gamediceservice.template.enums.GameState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameDicePlayResponse {
    private GameState gameState;
    private long win;
}
