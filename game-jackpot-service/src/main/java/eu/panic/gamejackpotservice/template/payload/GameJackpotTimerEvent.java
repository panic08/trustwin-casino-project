package eu.panic.gamejackpotservice.template.payload;

import eu.panic.gamejackpotservice.template.enums.GameEventType;
import eu.panic.gamejackpotservice.template.enums.JackpotRoom;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameJackpotTimerEvent {
    private GameEventType type;
    private JackpotRoom room;
    private int value;
}
