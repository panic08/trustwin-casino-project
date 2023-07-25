package eu.panic.gamejackpotservice.template.controller;

import eu.panic.gamejackpotservice.template.entity.Game;
import eu.panic.gamejackpotservice.template.enums.JackpotRoom;
import eu.panic.gamejackpotservice.template.hash.GameJackpotBetHash;
import eu.panic.gamejackpotservice.template.payload.GameJackpotPlayRequest;
import eu.panic.gamejackpotservice.template.service.implement.GameJackpotServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/game/jackpot")
public class GameJackpotController {
    public GameJackpotController(GameJackpotServiceImpl gameJackpotService) {
        this.gameJackpotService = gameJackpotService;
    }

    private final GameJackpotServiceImpl gameJackpotService;
    @PostMapping("/play")
    private void handlePlayJackpot(
            @RequestHeader String jwtToken,
            @RequestBody GameJackpotPlayRequest gameJackpotPlayRequest
            ){
        gameJackpotService.handlePlayJackpot(jwtToken, gameJackpotPlayRequest);
    }

    @GetMapping("/getLast")
    private Game getLastJackpotGame(@RequestParam JackpotRoom room){
        return gameJackpotService.getLastJackpotGame(room);
    }

    @GetMapping("/getAllBets")
    private List<GameJackpotBetHash> getAllJackpotBets(@RequestParam JackpotRoom room){
        return gameJackpotService.getAllJackpotBets(room);
    }

}
