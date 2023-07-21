package eu.panic.gametowerservice.template.payload;

import eu.panic.gametowerservice.template.enums.GameState;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GameTowerPlayResponse {
    private GameState gameState;
    private long win;
    private double coefficient;
    private List<Integer> picked;
    private int[][] mines;
}
