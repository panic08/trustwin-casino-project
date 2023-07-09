package eu.panic.gameovergoservice.template.payload;

import eu.panic.gameovergoservice.template.enums.GameState;
import eu.panic.gameovergoservice.template.enums.GameType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameOvergoPlayResponse {
    private GameState gameState;
    private GameType gameType;
    private Long amount;
    private double maxCoefficient;
}
