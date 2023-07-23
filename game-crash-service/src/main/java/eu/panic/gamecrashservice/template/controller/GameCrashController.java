package eu.panic.gamecrashservice.template.controller;

import eu.panic.gamecrashservice.template.entity.Game;
import eu.panic.gamecrashservice.template.hash.GameCrashBetHash;
import eu.panic.gamecrashservice.template.payload.GameCrashPlayRequest;
import eu.panic.gamecrashservice.template.payload.GameCrashTakeResponse;
import eu.panic.gamecrashservice.template.service.implement.GameCrashServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PostMapping("/take")
    private GameCrashTakeResponse handleBetTaking(@RequestHeader String jwtToken){
        return gameCrashService.handleBetTaking(jwtToken);
    }
    @GetMapping("/getAll")
    private List<Game> getLastTwentyCrashGames(){
        return gameCrashService.getLastTwentyCrashGames();
    }
    @GetMapping("/getLast")
    private Game getLastCrashGame(){
        return gameCrashService.getLastCrashGame();
    }
    @GetMapping("/getAllBets")
    private List<GameCrashBetHash> getAllCrashBets(){
        return gameCrashService.getAllCrashBets();
    }
}
