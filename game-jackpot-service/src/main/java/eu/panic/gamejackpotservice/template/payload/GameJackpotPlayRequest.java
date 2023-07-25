package eu.panic.gamejackpotservice.template.payload;

import eu.panic.gamejackpotservice.template.enums.JackpotRoom;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameJackpotPlayRequest {
    private long bet;
    private JackpotRoom room;
}
