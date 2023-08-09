package eu.panic.gamejackpotservice.template.service.implement;

import eu.panic.gamejackpotservice.template.dto.GameJackpotState;
import eu.panic.gamejackpotservice.template.dto.UserDto;
import eu.panic.gamejackpotservice.template.entity.Game;
import eu.panic.gamejackpotservice.template.enums.GameEventType;
import eu.panic.gamejackpotservice.template.enums.GameType;
import eu.panic.gamejackpotservice.template.enums.JackpotRoom;
import eu.panic.gamejackpotservice.template.exception.InsufficientFundsException;
import eu.panic.gamejackpotservice.template.exception.InvalidCredentialsException;
import eu.panic.gamejackpotservice.template.hash.GameJackpotBetHash;
import eu.panic.gamejackpotservice.template.payload.GameJackpotPlayRequest;
import eu.panic.gamejackpotservice.template.payload.GameJackpotNewGameEvent;
import eu.panic.gamejackpotservice.template.payload.GameJackpotTimerEvent;
import eu.panic.gamejackpotservice.template.repository.GameJackpotBetHashRepository;
import eu.panic.gamejackpotservice.template.repository.implement.GameRepositoryImpl;
import eu.panic.gamejackpotservice.template.repository.implement.UserRepositoryImpl;
import eu.panic.gamejackpotservice.template.service.GameJackpotService;
import eu.panic.gamejackpotservice.template.util.GameSessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@Slf4j
public class GameJackpotServiceImpl implements GameJackpotService {
    public GameJackpotServiceImpl(RestTemplate restTemplate, GameJackpotBetHashRepository gameJackpotBetHashRepository, UserRepositoryImpl userRepository, SimpMessagingTemplate simpMessagingTemplate, GameRepositoryImpl gameRepository) {
        this.restTemplate = restTemplate;
        this.gameJackpotBetHashRepository = gameJackpotBetHashRepository;
        this.userRepository = userRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.gameRepository = gameRepository;
    }

    private final RestTemplate restTemplate;
    private final GameJackpotBetHashRepository gameJackpotBetHashRepository;
    private final UserRepositoryImpl userRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final GameRepositoryImpl gameRepository;
    private static final GameJackpotState gameJackpotState = new GameJackpotState();
    private static final String JWT_URL = "http://localhost:8080/api/auth/getInfoByJwt";
    @Override
    public void handlePlayJackpot(String jwtToken, GameJackpotPlayRequest gameJackpotPlayRequest) {
        log.info("Starting method handlePlayJackpot on service {} method: handlePlayJackpot", GameJackpotServiceImpl.class);

        log.info("Receiving entity user by jwtToken on service {} method: handlePlayJackpot", GameJackpotServiceImpl.class);

        ResponseEntity<UserDto> userDtoResponseEntity =
                restTemplate.postForEntity(JWT_URL + "?jwtToken=" + jwtToken, null, UserDto.class);

        if (userDtoResponseEntity.getStatusCode().isError()){
            log.warn("Incorrect JWT token on service {} method: handlePlayJackpot", GameJackpotServiceImpl.class);
            throw new InvalidCredentialsException("Incorrect JWT token");
        }

        UserDto userDto = userDtoResponseEntity.getBody();

        switch (gameJackpotPlayRequest.getRoom()){
            case SMALL -> {
                if (gameJackpotPlayRequest.getBet() < 1 || gameJackpotPlayRequest.getBet() > 300){
                    log.warn("Incorrect Jackpot data on service {} method: handlePlayJackpot", GameJackpotServiceImpl.class);
                    throw new InvalidCredentialsException("Incorrect Jackpot data");
                }
            }
            case CLASSIC -> {
                if (gameJackpotPlayRequest.getBet() < 10 || gameJackpotPlayRequest.getBet() > 5000){
                    log.warn("Incorrect Jackpot data on service {} method: handlePlayJackpot", GameJackpotServiceImpl.class);
                    throw new InvalidCredentialsException("Incorrect Jackpot data");
                }
            }
            case MAJOR -> {
                if (gameJackpotPlayRequest.getBet() < 100 || gameJackpotPlayRequest.getBet() > 10000){
                    log.warn("Incorrect Jackpot data on service {} method: handlePlayJackpot", GameJackpotServiceImpl.class);
                    throw new InvalidCredentialsException("Incorrect Jackpot data");
                }
            }
            case MAX -> {
                if (gameJackpotPlayRequest.getBet() < 5000 || gameJackpotPlayRequest.getBet() > 100000){
                    log.warn("Incorrect Jackpot data on service {} method: handlePlayJackpot", GameJackpotServiceImpl.class);
                    throw new InvalidCredentialsException("Incorrect Jackpot data");
                }
            }
        }

        if (userDto.getBalance() < gameJackpotPlayRequest.getBet()){
            log.warn("You do not have enough money for this win on service {} method: handlePlayJackpot", GameJackpotServiceImpl.class);
            throw new InsufficientFundsException("You do not have enough money for this win");
        }

        switch (gameJackpotPlayRequest.getRoom()){
            case SMALL -> {
                if (gameJackpotState.getGameJackpotTypeSmall().getIsStarted()){
                    log.warn("You cannot place a win as the game has already started on service {} method: handlePlayJackpot",
                            GameJackpotServiceImpl.class);
                    throw new InvalidCredentialsException("You cannot place a win as the game has already started");
                }
            }
            case CLASSIC -> {
                if (gameJackpotState.getGameJackpotTypeClassic().getIsStarted()){
                    log.warn("You cannot place a win as the game has already started on service {} method: handlePlayJackpot",
                            GameJackpotServiceImpl.class);
                    throw new InvalidCredentialsException("You cannot place a win as the game has already started");
                }
            }
            case MAJOR -> {
                if (gameJackpotState.getGameJackpotTypeMajor().getIsStarted()){
                    log.warn("You cannot place a win as the game has already started on service {} method: handlePlayJackpot",
                            GameJackpotServiceImpl.class);
                    throw new InvalidCredentialsException("You cannot place a win as the game has already started");
                }
            }
            case MAX -> {
                if (gameJackpotState.getGameJackpotTypeMax().getIsStarted()){
                    log.warn("You cannot place a win as the game has already started on service {} method: handlePlayJackpot",
                            GameJackpotServiceImpl.class);
                    throw new InvalidCredentialsException("You cannot place a win as the game has already started");
                }
            }
        }

        log.info("Updating user balance by Id on service {} method: handlePlayJackpot", GameJackpotServiceImpl.class);

        userRepository.updateBalanceById(userDto.getBalance() - gameJackpotPlayRequest.getBet(), userDto.getId());

        log.info("Creating new hash gameJackpotHash on service {} method: handlePlayJackpot", GameJackpotServiceImpl.class);

        GameJackpotBetHash gameJackpotBetHash = new GameJackpotBetHash();

        gameJackpotBetHash.setId(UUID.randomUUID().toString());
        gameJackpotBetHash.setBet(gameJackpotPlayRequest.getBet());
        gameJackpotBetHash.setRoom(gameJackpotPlayRequest.getRoom());
        gameJackpotBetHash.setUser(userDto);
        gameJackpotBetHash.setTimestamp(System.currentTimeMillis());

        List<GameJackpotBetHash> gameJackpotBetHashList = gameJackpotBetHashRepository.findAllByRoom(gameJackpotPlayRequest.getRoom());

        long maxTicketEnds = 0;

        if (gameJackpotBetHashList.isEmpty()){
            gameJackpotBetHash.setTicketsStarts(1L);
            gameJackpotBetHash.setTicketsEnds(gameJackpotPlayRequest.getBet() * 10);
        }else{
            for (GameJackpotBetHash key : gameJackpotBetHashList){
                if (key.getTicketsEnds() > maxTicketEnds){
                    maxTicketEnds = key.getTicketsEnds();
                }
            }

            gameJackpotBetHash.setTicketsStarts(maxTicketEnds + 1);
            gameJackpotBetHash.setTicketsEnds(maxTicketEnds + (gameJackpotBetHash.getBet() * 10));
        }

        log.info("Saving a hash gameJackpotBetHash on service {} method: handlePlayJackpot", GameJackpotServiceImpl.class);

        gameJackpotBetHashRepository.save(gameJackpotBetHash);

        switch (gameJackpotPlayRequest.getRoom()){
            case SMALL -> {
                if (!gameJackpotState.getGameJackpotTypeSmall().getIsPrevStarted()){
                    new Thread(() -> {
                        startGameJackpot(gameJackpotPlayRequest.getRoom());
                    }).start();
                }
            }
            case CLASSIC -> {
                if (!gameJackpotState.getGameJackpotTypeClassic().getIsPrevStarted()){
                    new Thread(() -> {
                        startGameJackpot(gameJackpotPlayRequest.getRoom());
                    }).start();
                }
            }
            case MAJOR -> {
                if (!gameJackpotState.getGameJackpotTypeMajor().getIsPrevStarted()){
                    new Thread(() -> {
                        startGameJackpot(gameJackpotPlayRequest.getRoom());
                    }).start();
                }
            }
            case MAX -> {
                if (!gameJackpotState.getGameJackpotTypeMax().getIsPrevStarted()){
                    new Thread(() -> {
                        startGameJackpot(gameJackpotPlayRequest.getRoom());
                    }).start();
                }
            }
        }

        switch (gameJackpotPlayRequest.getRoom()){
            case SMALL -> simpMessagingTemplate.convertAndSend("/bets/small/topic", gameJackpotBetHashRepository.findAllByRoom(gameJackpotPlayRequest.getRoom()));

            case CLASSIC -> simpMessagingTemplate.convertAndSend("/bets/classic/topic", gameJackpotBetHashRepository.findAllByRoom(gameJackpotPlayRequest.getRoom()));

            case MAJOR -> simpMessagingTemplate.convertAndSend("/bets/major/topic", gameJackpotBetHashRepository.findAllByRoom(gameJackpotPlayRequest.getRoom()));

            case MAX -> simpMessagingTemplate.convertAndSend("/bets/max/topic", gameJackpotBetHashRepository.findAllByRoom(gameJackpotPlayRequest.getRoom()));
        }
    }

    @Override
    public Game getLastJackpotGame(JackpotRoom room) {
        log.info("Starting method getLastJackpotGame on service {} method: getLastJackpotGame", GameJackpotServiceImpl.class);

        switch (room){
            case SMALL -> {
                return gameRepository.findLastGameByGameType(GameType.JACKPOT_SMALL);
            }
            case CLASSIC -> {
                return gameRepository.findLastGameByGameType(GameType.JACKPOT_CLASSIC);
            }
            case MAJOR -> {
                return gameRepository.findLastGameByGameType(GameType.JACKPOT_MAJOR);
            }
            case MAX -> {
                return gameRepository.findLastGameByGameType(GameType.JACKPOT_MAX);
            }
        }
        return null;
    }

    @Override
    public List<GameJackpotBetHash> getAllJackpotBets(JackpotRoom room) {
        log.info("Starting method getAllJackpotBets on service {} method: getAllJackpotBets", GameJackpotServiceImpl.class);

        return gameJackpotBetHashRepository.findAllByRoom(room);
    }

    private void startGameJackpot(JackpotRoom room){
        log.info("Starting method startGameJackpot on service {} method: startGameJackpot", GameJackpotServiceImpl.class);

        log.info("Finding hashes by Room on service {} method: startGameJackpot", GameJackpotServiceImpl.class);

        List<GameJackpotBetHash> gameJackpotBetHashList = gameJackpotBetHashRepository.findAllByRoom(room);
        Set<String> usernames = new HashSet<>();

        for (GameJackpotBetHash betHash : gameJackpotBetHashList){
            usernames.add(betHash.getUser().getUsername());
        }

        for (GameJackpotBetHash betHash : gameJackpotBetHashList) {
            for (String username : usernames){
                if (!betHash.getUser().getUsername().equals(username)){
                    switch (room){
                        case SMALL -> gameJackpotState.getGameJackpotTypeSmall().setIsPrevStarted(true);

                        case CLASSIC -> gameJackpotState.getGameJackpotTypeClassic().setIsPrevStarted(true);

                        case MAJOR -> gameJackpotState.getGameJackpotTypeMajor().setIsPrevStarted(true);

                        case MAX -> gameJackpotState.getGameJackpotTypeMax().setIsPrevStarted(true);
                    }
                    break;
                }
            }
        }

        switch (room){
            case SMALL -> {
                if (!gameJackpotState.getGameJackpotTypeSmall().getIsPrevStarted()){
                    return;
                }
            }
            case CLASSIC -> {
                if (!gameJackpotState.getGameJackpotTypeClassic().getIsPrevStarted()){
                    return;
                }
            }
            case MAJOR -> {
                if (!gameJackpotState.getGameJackpotTypeMajor().getIsPrevStarted()){
                    return;
                }
            }
            case MAX -> {
                if (!gameJackpotState.getGameJackpotTypeMax().getIsPrevStarted()){
                    return;
                }
            }
        }

        int currentSeconds = 30;

        GameJackpotTimerEvent gameCrashTimerEvent = new GameJackpotTimerEvent();

        gameCrashTimerEvent.setType(GameEventType.TIMER);
        gameCrashTimerEvent.setRoom(room);

        while (currentSeconds >= 0) {
            gameCrashTimerEvent.setValue(currentSeconds);

            switch (room){
                case SMALL -> simpMessagingTemplate.convertAndSend("/slider/small/topic", gameCrashTimerEvent);

                case CLASSIC -> simpMessagingTemplate.convertAndSend("/slider/classic/topic", gameCrashTimerEvent);

                case MAJOR -> simpMessagingTemplate.convertAndSend("/slider/major/topic", gameCrashTimerEvent);

                case MAX -> simpMessagingTemplate.convertAndSend("/slider/max/topic", gameCrashTimerEvent);
            }

            currentSeconds -= 1;

            if (currentSeconds == 3){
                switch (room){
                    case SMALL -> gameJackpotState.getGameJackpotTypeSmall().setIsStarted(true);

                    case CLASSIC -> gameJackpotState.getGameJackpotTypeClassic().setIsStarted(true);

                    case MAJOR -> gameJackpotState.getGameJackpotTypeMajor().setIsStarted(true);

                    case MAX -> gameJackpotState.getGameJackpotTypeMax().setIsStarted(true);
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        GameSessionUtil gameSessionUtil = new GameSessionUtil("00000000000000000011b3e92e82e0f9939093dccc3614647686c20e5ebe3aa6",
                "000000000000000000223b7a2298fb1c6c75fb0efc28a4c56853ff4112ec6bc9");

        gameSessionUtil.generateSalt();

        log.info("Finding hashes by Room on service {} method: startGameJackpot", GameJackpotServiceImpl.class);

        List<GameJackpotBetHash> gameJackpotBetHashList1 = gameJackpotBetHashRepository.findAllByRoom(room);

        long ticketsCount = 0;
        long bank = 0;

        long happyTicket;

        for (GameJackpotBetHash gameJackpotBetHash : gameJackpotBetHashList1){
            ticketsCount += (gameJackpotBetHash.getBet() * 10);
            bank += gameJackpotBetHash.getBet();
        }

        happyTicket = (long) Math.floor(((gameSessionUtil.generateRandomNumber() * ticketsCount) + 1));

        GameJackpotBetHash winner = null;

        for (GameJackpotBetHash gameJackpotBetHash : gameJackpotBetHashList1){
            if (gameJackpotBetHash.getTicketsStarts() <= happyTicket && gameJackpotBetHash.getTicketsEnds() >= happyTicket){
                winner = gameJackpotBetHash;
            }
        }

        GameJackpotNewGameEvent gameJackpotNewGameEvent = new GameJackpotNewGameEvent();

        gameJackpotNewGameEvent.setType(GameEventType.NEW_GAME);
        gameJackpotNewGameEvent.setRoom(room);
        gameJackpotNewGameEvent.setWinner(winner.getUser());
        gameJackpotNewGameEvent.setBet(winner.getBet());
        gameJackpotNewGameEvent.setWin((long) Math.floor(bank - (bank * 0.06)));

        switch (room){
            case SMALL -> simpMessagingTemplate.convertAndSend("/slider/small/topic", gameJackpotNewGameEvent);

            case CLASSIC -> simpMessagingTemplate.convertAndSend("/slider/classic/topic", gameJackpotNewGameEvent);

            case MAJOR -> simpMessagingTemplate.convertAndSend("/slider/major/topic", gameJackpotNewGameEvent);

            case MAX -> simpMessagingTemplate.convertAndSend("/slider/max/topic", gameJackpotNewGameEvent);
        }

        log.info("Finding and updating user balance by Id on service {} method: startGameJackpot", GameJackpotServiceImpl.class);

        userRepository.updateBalanceById(userRepository.findBalanceById(winner.getUser().getId()) +
                (long) Math.floor(bank - (bank * 0.06)), winner.getUser().getId());

        currentSeconds = 15;

        while (currentSeconds >= 0) {
            gameCrashTimerEvent.setValue(currentSeconds);

            switch (room){
                case SMALL -> simpMessagingTemplate.convertAndSend("/slider/small/topic", gameCrashTimerEvent);

                case CLASSIC -> simpMessagingTemplate.convertAndSend("/slider/classic/topic", gameCrashTimerEvent);

                case MAJOR -> simpMessagingTemplate.convertAndSend("/slider/major/topic", gameCrashTimerEvent);

                case MAX -> simpMessagingTemplate.convertAndSend("/slider/max/topic", gameCrashTimerEvent);
            }


            currentSeconds -= 1;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        switch (room){
            case SMALL -> {
                gameJackpotState.getGameJackpotTypeSmall().setIsStarted(false);
                gameJackpotState.getGameJackpotTypeSmall().setIsPrevStarted(false);
            }

            case CLASSIC -> {
                gameJackpotState.getGameJackpotTypeClassic().setIsStarted(false);
                gameJackpotState.getGameJackpotTypeClassic().setIsPrevStarted(false);
            }

            case MAJOR -> {
                gameJackpotState.getGameJackpotTypeMajor().setIsStarted(false);
                gameJackpotState.getGameJackpotTypeMajor().setIsPrevStarted(false);
            }

            case MAX -> {
                gameJackpotState.getGameJackpotTypeMax().setIsStarted(false);
                gameJackpotState.getGameJackpotTypeMax().setIsPrevStarted(false);
            }
        }

        log.info("Creating a new entity game on service {} method: startGameJackpot", GameJackpotServiceImpl.class);

        Game game = new Game();

        switch (room){
            case SMALL -> game.setGameType(GameType.JACKPOT_SMALL);

            case CLASSIC -> game.setGameType(GameType.JACKPOT_CLASSIC);

            case MAJOR -> game.setGameType(GameType.JACKPOT_MAJOR);

            case MAX -> game.setGameType(GameType.JACKPOT_MAX);
        }
        game.setNickname("null");
        game.setUsername("null");
        game.setBet(0L);
        game.setWin(0L);
        game.setCoefficient(0D);
        game.setClientSeed("000000000000000000223b7a2298fb1c6c75fb0efc28a4c56853ff4112ec6bc9");
        game.setServerSeed("00000000000000000011b3e92e82e0f9939093dccc3614647686c20e5ebe3aa6");
        game.setSalt(gameSessionUtil.getSalt());
        game.setTimestamp(System.currentTimeMillis());

        log.info("Saving an entity game on service {} method: startGameJackpot", GameJackpotServiceImpl.class);

        gameRepository.save(game);

        gameJackpotBetHashRepository.deleteAll(gameJackpotBetHashRepository.findAllByRoom(room));

        switch (room){
            case SMALL -> simpMessagingTemplate.convertAndSend("/bets/small/topic", new ArrayList<>());

            case CLASSIC -> simpMessagingTemplate.convertAndSend("/bets/classic/topic", new ArrayList<>());

            case MAJOR -> simpMessagingTemplate.convertAndSend("/bets/major/topic", new ArrayList<>());

            case MAX -> simpMessagingTemplate.convertAndSend("/bets/max/topic", new ArrayList<>());
        }
    }
}
