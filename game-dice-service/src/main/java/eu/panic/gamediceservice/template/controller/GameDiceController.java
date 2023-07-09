package eu.panic.gamediceservice.template.controller;

import eu.panic.gamediceservice.template.entity.Game;
import eu.panic.gamediceservice.template.payload.GameDicePlayRequest;
import eu.panic.gamediceservice.template.payload.GameDicePlayResponse;
import eu.panic.gamediceservice.template.service.implement.GameDiceServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game/dice")
public class GameDiceController {
    public GameDiceController(GameDiceServiceImpl gameDiceService) {
        this.gameDiceService = gameDiceService;
    }

    private final GameDiceServiceImpl gameDiceService;
    @PostMapping("/play")
    private GameDicePlayResponse handlePlayDice(
            @RequestHeader String jwtToken,
            @RequestBody GameDicePlayRequest gameDicePlayRequest
    ){
        return gameDiceService.handlePlayDice(jwtToken, gameDicePlayRequest);
    }
    @PostMapping("/getLast")
    private Game getLastDiceGame(@RequestHeader String jwtToken){
        return gameDiceService.getLastDiceGame(jwtToken);
    }
}
