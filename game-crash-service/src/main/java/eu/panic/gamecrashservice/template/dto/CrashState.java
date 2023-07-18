package eu.panic.gamecrashservice.template.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrashState {
    private Boolean isStarted;
    private Double coefficient;
}
