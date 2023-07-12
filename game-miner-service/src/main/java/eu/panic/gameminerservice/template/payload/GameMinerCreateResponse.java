package eu.panic.gameminerservice.template.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GameMinerCreateResponse {
    private long amount;
    private double coefficient;
    private List<Integer> picked;
}
