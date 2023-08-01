package eu.panic.gamejackpotservice.template.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameJackpotState {
    private Small gameJackpotTypeSmall;
    private Classic gameJackpotTypeClassic;
    private Major gameJackpotTypeMajor;
    private Max gameJackpotTypeMax;

    public GameJackpotState() {
        this.gameJackpotTypeSmall = new Small();
        this.gameJackpotTypeClassic = new Classic();
        this.gameJackpotTypeMajor = new Major();
        this.gameJackpotTypeMax = new Max();
    }

    @Getter
    @Setter
    public static class Small{
        private Boolean isStarted = false;
        private Boolean isPrevStarted = false;
    }
    @Getter
    @Setter
    public static class Classic{
        private Boolean isStarted = false;
        private Boolean isPrevStarted = false;
    }
    @Getter
    @Setter
    public static class Major{
        private Boolean isStarted = false;
        private Boolean isPrevStarted = false;
    }
    @Getter
    @Setter
    public static class Max{
        private Boolean isStarted = false;
        private Boolean isPrevStarted = false;
    }
}
