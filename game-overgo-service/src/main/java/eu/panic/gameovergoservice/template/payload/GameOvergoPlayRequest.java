package eu.panic.gameovergoservice.template.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameOvergoPlayRequest {
    private long amount;
    private double chance;
}
