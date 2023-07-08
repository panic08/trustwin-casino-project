package eu.panic.gamediceservice.template.controller;

import eu.panic.gamediceservice.template.payload.GameDiceRequest;
import eu.panic.gamediceservice.template.payload.GameDiceResponse;
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
    private GameDiceResponse handlePlayDice(
            @RequestHeader String jwtToken,
            @RequestBody GameDiceRequest gameDiceRequest
    ){
        return gameDiceService.handlePlayDice(jwtToken, gameDiceRequest);
    }
}
