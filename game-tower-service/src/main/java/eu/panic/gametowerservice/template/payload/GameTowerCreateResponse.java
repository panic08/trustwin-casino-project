package eu.panic.gametowerservice.template.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GameTowerCreateResponse {
    private long win;
    private double coefficient;
    private List<Integer> picked;
}
