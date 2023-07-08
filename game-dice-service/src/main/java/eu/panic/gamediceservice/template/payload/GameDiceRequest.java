package eu.panic.gamediceservice.template.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameDiceRequest {
    private Long amount;
    private Double chance;
}
