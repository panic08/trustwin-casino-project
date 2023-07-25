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
    @Getter
    @Setter
    public static class Small{
        private Boolean isStarted;
        private Boolean isPrevStarted;
    }
    @Getter
    @Setter
    public static class Classic{
        private Boolean isStarted;
        private Boolean isPrevStarted;
    }
    @Getter
    @Setter
    public static class Major{
        private Boolean isStarted;
        private Boolean isPrevStarted;
    }
    @Getter
    @Setter
    public static class Max{
        private Boolean isStarted;
        private Boolean isPrevStarted;
    }
}
