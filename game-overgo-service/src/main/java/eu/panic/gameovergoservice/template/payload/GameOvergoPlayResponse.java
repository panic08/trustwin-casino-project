package eu.panic.gameovergoservice.template.payload;

import eu.panic.gameovergoservice.template.enums.GameState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameOvergoPlayResponse {
    private GameState gameState;
    private long bet;
    private long win;
    private double maxCoefficient;
}
