package eu.panic.managementgameservice.template.controller;

import eu.panic.managementgameservice.template.payload.GameMessage;
import eu.panic.managementgameservice.template.service.implement.GameServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {
    public GameController(GameServiceImpl gameService) {
        this.gameService = gameService;
    }

    private final GameServiceImpl gameService;
    @GetMapping("/getAll")
    private List<GameMessage> getAllGameMessages(){
        return gameService.getAllGameMessages();
    }
}
