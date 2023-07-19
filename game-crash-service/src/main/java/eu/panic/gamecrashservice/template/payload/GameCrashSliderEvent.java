package eu.panic.gamecrashservice.template.payload;

import eu.panic.gamecrashservice.template.enums.GameEventType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameCrashSliderEvent {
    private GameEventType type;
    private double value;
    private boolean isCrashed;
}
