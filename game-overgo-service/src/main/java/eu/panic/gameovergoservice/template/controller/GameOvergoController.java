package eu.panic.gameovergoservice.template.controller;

import eu.panic.gameovergoservice.template.entity.Game;
import eu.panic.gameovergoservice.template.payload.GameOvergoPlayRequest;
import eu.panic.gameovergoservice.template.payload.GameOvergoPlayResponse;
import eu.panic.gameovergoservice.template.service.implement.GameOvergoServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game/overgo")
public class GameOvergoController {
    public GameOvergoController(GameOvergoServiceImpl gameOvergoService) {
        this.gameOvergoService = gameOvergoService;
    }

    private final GameOvergoServiceImpl gameOvergoService;
    @PostMapping("/play")
    private GameOvergoPlayResponse handlePlayOvergo(
            @RequestHeader String jwtToken,
            @RequestBody GameOvergoPlayRequest gameOvergoPlayRequest
            ){
        return gameOvergoService.handlePlayOvergo(jwtToken, gameOvergoPlayRequest);
    }
    @PostMapping("/getLast")
    private Game getLastOvergoGame(@RequestHeader String jwtToken){
        return gameOvergoService.getLastOvergoGame(jwtToken);
    }
}
