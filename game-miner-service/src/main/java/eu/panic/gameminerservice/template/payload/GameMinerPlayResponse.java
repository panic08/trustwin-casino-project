package eu.panic.gameminerservice.template.payload;

import eu.panic.gameminerservice.template.enums.GameState;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GameMinerPlayResponse {
    private GameState gameState;
    private long amount;
    private double coefficient;
    private List<Integer> picked;
    private List<Integer> mines;
}
