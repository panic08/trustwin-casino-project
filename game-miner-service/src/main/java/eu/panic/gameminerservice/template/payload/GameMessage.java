package eu.panic.gameminerservice.template.payload;

import eu.panic.gameminerservice.template.dto.UserDto;
import eu.panic.gameminerservice.template.enums.GameType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameMessage {
    private GameType gameType;
    private UserDto user;
    private long bet;
    private long win;
    private double coefficient;
    private long timestamp;
}
