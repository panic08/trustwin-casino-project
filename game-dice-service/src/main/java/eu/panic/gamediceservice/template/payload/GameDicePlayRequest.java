package eu.panic.gamediceservice.template.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameDicePlayRequest {
    private Long amount;
    private Double chance;
}
