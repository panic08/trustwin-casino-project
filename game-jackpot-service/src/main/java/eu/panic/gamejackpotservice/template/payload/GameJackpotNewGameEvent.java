package eu.panic.gamejackpotservice.template.payload;

import eu.panic.gamejackpotservice.template.dto.UserDto;
import eu.panic.gamejackpotservice.template.enums.GameEventType;
import eu.panic.gamejackpotservice.template.enums.JackpotRoom;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameJackpotNewGameEvent {
    private GameEventType type;
    private long happyTicket;
    private UserDto winner;
    private JackpotRoom room;
    private long bet;
    private long win;
}
