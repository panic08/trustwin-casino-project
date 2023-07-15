package eu.panic.managementgameservice.template.payload;

import eu.panic.managementgameservice.template.entity.User;
import eu.panic.managementgameservice.template.enums.GameType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameMessage {
    private GameType gameType;
    private User user;
    private long bet;
    private long win;
    private double coefficient;
    private long timestamp;
}
