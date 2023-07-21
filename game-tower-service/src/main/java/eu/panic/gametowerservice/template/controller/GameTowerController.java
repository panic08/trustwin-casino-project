package eu.panic.gametowerservice.template.controller;

import eu.panic.gametowerservice.template.entity.Game;
import eu.panic.gametowerservice.template.payload.*;
import eu.panic.gametowerservice.template.service.implement.GameTowerServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game/tower")
public class GameTowerController {
    public GameTowerController(GameTowerServiceImpl gameTowerService) {
        this.gameTowerService = gameTowerService;
    }

    private final GameTowerServiceImpl gameTowerService;
    @PostMapping("/create")
    private GameTowerCreateResponse createTowerSession(
            @RequestHeader String jwtToken,
            @RequestBody GameTowerCreateRequest gameTowerCreateRequest
            ){
        return gameTowerService.createTowerSession(jwtToken, gameTowerCreateRequest);
    }

    @PostMapping("/play")
    private GameTowerPlayResponse handlePlayTower(
            @RequestHeader String jwtToken,
            @RequestBody GameTowerPlayRequest gameTowerPlayRequest
            ){
        return gameTowerService.handlePlayTower(jwtToken, gameTowerPlayRequest);
    }

    @PostMapping("/take")
    private GameTowerPlayResponse handleBetTaking(@RequestHeader String jwtToken){
        return gameTowerService.handleBetTaking(jwtToken);
    }

    @GetMapping("/getCurrent")
    private GameTowerGetCurrentResponse getCurrentTowerGame(@RequestHeader String jwtToken){
        return gameTowerService.getCurrentTowerGame(jwtToken);
    }

    @GetMapping("/getLast")
    private Game getLastTowerGame(@RequestHeader String jwtToken){
        return gameTowerService.getLastTowerGame(jwtToken);
    }
}
