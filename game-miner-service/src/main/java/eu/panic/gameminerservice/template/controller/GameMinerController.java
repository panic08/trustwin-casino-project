package eu.panic.gameminerservice.template.controller;

import eu.panic.gameminerservice.template.entity.Game;
import eu.panic.gameminerservice.template.hash.MinerSessionHash;
import eu.panic.gameminerservice.template.payload.GameMinerCreateRequest;
import eu.panic.gameminerservice.template.payload.GameMinerCreateResponse;
import eu.panic.gameminerservice.template.payload.GameMinerPlayRequest;
import eu.panic.gameminerservice.template.payload.GameMinerPlayResponse;
import eu.panic.gameminerservice.template.service.implement.GameMinerServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game/miner")
public class GameMinerController {
    public GameMinerController(GameMinerServiceImpl gameMinerService) {
        this.gameMinerService = gameMinerService;
    }

    private final GameMinerServiceImpl gameMinerService;
    @PostMapping("/create")
    private GameMinerCreateResponse createMinerSession(
            @RequestHeader String jwtToken,
            @RequestBody GameMinerCreateRequest gameMinerCreateRequest
            ){
        return gameMinerService.handleCreatingMinerSession(jwtToken, gameMinerCreateRequest);
    }
    @PostMapping("/play")
    private GameMinerPlayResponse handlePlayMiner(
            @RequestHeader String jwtToken,
            @RequestBody GameMinerPlayRequest gameMinerPlayRequest
            ){
        return gameMinerService.handlePlayMiner(jwtToken, gameMinerPlayRequest);
    }
    @PostMapping("/take")
    private GameMinerPlayResponse handleBetTaking(
            @RequestHeader String jwtToken
    ){
        return gameMinerService.handleBetTaking(jwtToken);
    }
    @GetMapping("/getCurrent")
    private MinerSessionHash getCurrentMinerGame(
            @RequestHeader String jwtToken
    ){
        return gameMinerService.getCurrentMinerGame(jwtToken);
    }
    @GetMapping ("/getLast")
    private Game getLastMinerGame(
            @RequestHeader String jwtToken
    ){
        return gameMinerService.getLastMinerGame(jwtToken);
    }
}
