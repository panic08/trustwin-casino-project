package eu.panic.gamecrashservice.template.controller;

import eu.panic.gamecrashservice.template.payload.GameCrashPlayRequest;
import eu.panic.gamecrashservice.template.service.implement.GameCrashServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game/crash")
public class GameCrashController {
    public GameCrashController(GameCrashServiceImpl gameCrashService) {
        this.gameCrashService = gameCrashService;
    }

    private final GameCrashServiceImpl gameCrashService;
    @PostMapping("/play")
    private void handlePlayCrash(
            @RequestHeader String jwtToken,
            @RequestBody GameCrashPlayRequest gameCrashPlayRequest
            ){
        gameCrashService.handlePlayCrash(jwtToken, gameCrashPlayRequest);
    }
}
