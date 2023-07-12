package eu.panic.gameovergoservice.template.payload;

import eu.panic.gameovergoservice.template.dto.UserDto;
import eu.panic.gameovergoservice.template.enums.GameType;
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
