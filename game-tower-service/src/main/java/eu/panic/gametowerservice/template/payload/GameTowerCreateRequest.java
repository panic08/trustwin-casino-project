package eu.panic.gametowerservice.template.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameTowerCreateRequest {
    private long bet;
    private int minesCount;
}
