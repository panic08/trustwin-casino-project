package eu.panic.gamecrashservice.template.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameCrashTakeResponse {
    private long bet;
    private long win;
    private double coefficient;
}
