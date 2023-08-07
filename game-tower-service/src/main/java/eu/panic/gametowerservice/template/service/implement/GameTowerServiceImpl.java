package eu.panic.gametowerservice.template.service.implement;

import com.google.gson.Gson;
import eu.panic.gametowerservice.template.dto.UserDto;
import eu.panic.gametowerservice.template.entity.Game;
import eu.panic.gametowerservice.template.enums.GameState;
import eu.panic.gametowerservice.template.enums.GameType;
import eu.panic.gametowerservice.template.exception.InsufficientFundsException;
import eu.panic.gametowerservice.template.exception.InvalidCredentialsException;
import eu.panic.gametowerservice.template.hash.GameTowerSessionHash;
import eu.panic.gametowerservice.template.payload.*;
import eu.panic.gametowerservice.template.repository.GameTowerSessionHashRepository;
import eu.panic.gametowerservice.template.repository.implement.GameRepositoryImpl;
import eu.panic.gametowerservice.template.repository.implement.UserRepositoryImpl;
import eu.panic.gametowerservice.template.service.GameTowerService;
import eu.panic.gametowerservice.template.util.GameSessionUtil;
import eu.panic.gametowerservice.template.util.GameTowerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
@Slf4j
public class GameTowerServiceImpl implements GameTowerService {
    public GameTowerServiceImpl(GameTowerSessionHashRepository gameTowerSessionHashRepository, UserRepositoryImpl userRepository, GameRepositoryImpl gameRepository, RestTemplate restTemplate, RabbitTemplate rabbitTemplate) {
        this.gameTowerSessionHashRepository = gameTowerSessionHashRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.restTemplate = restTemplate;
        this.rabbitTemplate = rabbitTemplate;
    }

    private final GameTowerSessionHashRepository gameTowerSessionHashRepository;
    private final UserRepositoryImpl userRepository;
    private final GameRepositoryImpl gameRepository;
    private final RestTemplate restTemplate;
    private final RabbitTemplate rabbitTemplate;
    private static final String JWT_URL = "http://localhost:8080/api/auth/getInfoByJwt";
    private final Gson gson = new Gson();
    @Override
    public GameTowerCreateResponse createTowerSession(String jwtToken, GameTowerCreateRequest gameTowerCreateRequest) {
        log.info("Starting method createTowerSession on service {} method: createTowerSession", GameTowerServiceImpl.class);

        log.info("Receiving entity user by JWT token on service {} method: createTowerSession", GameTowerServiceImpl.class);

        ResponseEntity<UserDto> userDtoResponseEntity =
                restTemplate.postForEntity(JWT_URL + "?jwtToken=" + jwtToken, null, UserDto.class);

        if (userDtoResponseEntity.getStatusCode().isError()){
            log.warn("Incorrect JWT token on service {} method: createTowerSession", GameTowerServiceImpl.class);
            throw new InvalidCredentialsException("Incorrect JWT token");
        }

        UserDto userDto = userDtoResponseEntity.getBody();

        if (gameTowerCreateRequest.getBet() < 1 || gameTowerCreateRequest.getBet() > 100000 || gameTowerCreateRequest.getMinesCount() < 1
                || gameTowerCreateRequest.getMinesCount() > 4){
            log.warn("Incorrect Tower data on service {} method: createTowerSession", GameTowerServiceImpl.class);
            throw new InvalidCredentialsException("Incorrect Tower data");
        }

        if (userDto.getBalance() < gameTowerCreateRequest.getBet()){
            log.warn("You do not have enough money for this bet on service {} method: createTowerSession", GameTowerServiceImpl.class);
            throw new InsufficientFundsException("You do not have enough money for this bet");
        }

        if (!userDto.getIsAccountNonLocked()){
            log.warn("You have been temporarily blocked. For all questions contact support on service {}" +
                    "method: createTowerSession", GameTowerServiceImpl.class);
            throw new InvalidCredentialsException("You have been temporarily blocked. For all questions contact support");
        }

        GameTowerSessionHash gameTowerSessionHash =
                gameTowerSessionHashRepository.findGameTowerSessionHashByUsername(userDto.getUsername());

        if (gameTowerSessionHash != null){
            log.warn("Finish the previous game session before starting a new one on service {} method: createTowerSession", GameTowerServiceImpl.class);
            throw new InvalidCredentialsException("Finish the previous game session before starting a new one");
        }

        log.info("Updating entity user balance by Id on service {} method: createTowerSession", GameTowerServiceImpl.class);

        userRepository.updateBalanceById(userDto.getBalance() - gameTowerCreateRequest.getBet(), userDto.getId());

        GameSessionUtil gameSessionUtil = new GameSessionUtil(userDto.getData().getServerSeed(), userDto.getData().getClientSeed());

        double[] towerNumbers = new double[40];

        for (int  i = 0; i < 40; i++){
            gameSessionUtil.generateSalt();

            towerNumbers[i] = gameSessionUtil.generateRandomNumber();
        }

        log.info("Creating gameTowerSessionHash1 hash on service {} method: createTowerSession", GameTowerServiceImpl.class);

        GameTowerSessionHash gameTowerSessionHash1 = new GameTowerSessionHash();

        gameTowerSessionHash1.setUsername(userDto.getUsername());
        gameTowerSessionHash1.setStep(0);
        gameTowerSessionHash1.setSalt(gameSessionUtil.getSalt());
        gameTowerSessionHash1.setBet(gameTowerCreateRequest.getBet());
        gameTowerSessionHash1.setWin(gameTowerSessionHash1.getBet());
        gameTowerSessionHash1.setPicked(new ArrayList<>());
        gameTowerSessionHash1.setMines(gson.toJson(GameTowerUtil.minesShuffling(towerNumbers, gameTowerCreateRequest.getMinesCount())));
        gameTowerSessionHash1.setCoefficient(1.00);
        gameTowerSessionHash1.setTimestamp(System.currentTimeMillis());

        log.info("Saving a hash gameTowerSessionHash1 on service {} method: createTowerSession", GameTowerServiceImpl.class);

        gameTowerSessionHashRepository.save(gameTowerSessionHash1);

        log.info("Creating a response for this method on service {} method: createTowerSession", GameTowerServiceImpl.class);

        GameTowerCreateResponse gameTowerCreateResponse = new GameTowerCreateResponse();

        gameTowerCreateResponse.setWin(gameTowerCreateRequest.getBet());
        gameTowerCreateResponse.setPicked(new ArrayList<>());
        gameTowerCreateResponse.setCoefficient(1.00);

        return gameTowerCreateResponse;
    }

    @Override
    public GameTowerPlayResponse handlePlayTower(String jwtToken, GameTowerPlayRequest gameTowerPlayRequest) {
        log.info("Starting method handlePlayTower on service {} method: handlePlayTower", GameTowerServiceImpl.class);

        log.info("Receiving entity user by JWT token on service {} method: handlePlayTower", GameTowerServiceImpl.class);

        ResponseEntity<UserDto> userDtoResponseEntity =
                restTemplate.postForEntity(JWT_URL + "?jwtToken=" + jwtToken, null, UserDto.class);

        if (userDtoResponseEntity.getStatusCode().isError()){
            log.warn("Incorrect JWT token on service {} method: handlePlayTower", GameTowerServiceImpl.class);
            throw new InvalidCredentialsException("Incorrect JWT token");
        }

        UserDto userDto = userDtoResponseEntity.getBody();

        if (gameTowerPlayRequest.getPick() < 0 || gameTowerPlayRequest.getPick() > 4){
            log.warn("Incorrect Tower data on service {} method: handlePlayTower", GameTowerServiceImpl.class);
            throw new InvalidCredentialsException("Incorrect Tower data");
        }

        GameTowerSessionHash gameTowerSessionHash =
                gameTowerSessionHashRepository.findGameTowerSessionHashByUsername(userDto.getUsername());

        if (gameTowerSessionHash == null){
            log.warn("Game session is out of date, start a new one on service {} method: handlePlayTower", GameTowerServiceImpl.class);
            throw new InvalidCredentialsException("Game session is out of date, start a new one");
        }

        int[][] mines = gson.fromJson(gameTowerSessionHash.getMines(), int[][].class);

        int step = gameTowerSessionHash.getStep();

        if (gameTowerSessionHash.getPicked() == null){
            gameTowerSessionHash.setPicked(new ArrayList<>());
        }

        gameTowerSessionHash.getPicked().add(gameTowerPlayRequest.getPick());

        log.info("Creating response for this method on service {} method: handlePlayTower", GameTowerServiceImpl.class);

        GameTowerPlayResponse gameTowerPlayResponse = new GameTowerPlayResponse();

        gameTowerPlayResponse.setPicked(gameTowerSessionHash.getPicked());


        for (int i = 0; i < mines[step].length; i++){
            if (mines[step][i] == gameTowerPlayRequest.getPick()){
                gameTowerPlayResponse.setGameState(GameState.LOSE);
                gameTowerPlayResponse.setWin(0);
                gameTowerPlayResponse.setMines(mines);
                gameTowerPlayResponse.setCoefficient(0);

                log.info("Deleting hash gameTowerSessionHash on service {} method: handlePlayTower", GameTowerServiceImpl.class);

                gameTowerSessionHashRepository.delete(gameTowerSessionHash);

                Game game = new Game();

                game.setBet(gameTowerSessionHash.getBet());
                game.setGameType(GameType.TOWER);
                game.setWin(0L);
                game.setBet(gameTowerSessionHash.getBet());
                game.setCoefficient(0D);
                game.setUsername(userDto.getUsername());
                game.setNickname(userDto.getPersonalData().getNickname());
                game.setServerSeed(userDto.getData().getServerSeed());
                game.setClientSeed(userDto.getData().getClientSeed());
                game.setSalt(gameTowerSessionHash.getSalt());
                game.setTimestamp(System.currentTimeMillis());

                log.info("Saving an entity game on service {} method: handlePlayTower", GameTowerServiceImpl.class);

                gameRepository.save(game);

                GameMessage gameMessage = new GameMessage();

                gameMessage.setGameType(GameType.TOWER);
                gameMessage.setWin(0);
                gameMessage.setBet(gameTowerSessionHash.getBet());
                gameMessage.setCoefficient(0);
                gameMessage.setUser(userDto);
                gameMessage.setTimestamp(System.currentTimeMillis());

                String jsonRequest = gson.toJson(gameMessage);

                rabbitTemplate.convertAndSend("game-queue", jsonRequest);

                return gameTowerPlayResponse;
            }
        }

        if (step == 9){
            gameTowerSessionHash.setCoefficient(gameTowerSessionHash.getCoefficient()*((double) 1 /((double) (5 - mines[0].length) /5)));

            gameTowerPlayResponse.setGameState(GameState.WIN);
            gameTowerPlayResponse.setCoefficient(Math.floor((gameTowerSessionHash.getCoefficient() - gameTowerSessionHash.getCoefficient() * 0.02) * 1e2) / 1e2);
            gameTowerPlayResponse.setWin((long) (gameTowerSessionHash.getBet() * gameTowerPlayResponse.getCoefficient()));
            gameTowerPlayResponse.setMines(mines);

            log.info("Deleting hash gameTowerSessionHash on service {} method: handlePlayTower", GameTowerServiceImpl.class);

            gameTowerSessionHashRepository.delete(gameTowerSessionHash);

            log.info("Updating entity user balance by Id on service {} method: handlePlayTower", GameTowerServiceImpl.class);

            userRepository.updateBalanceById(userDto.getBalance() + gameTowerPlayResponse.getWin(), userDto.getId());

            Game game = new Game();

            game.setBet(gameTowerSessionHash.getBet());
            game.setGameType(GameType.TOWER);
            game.setWin(gameTowerPlayResponse.getWin());
            game.setBet(gameTowerSessionHash.getBet());
            game.setCoefficient(gameTowerPlayResponse.getCoefficient());
            game.setUsername(userDto.getUsername());
            game.setNickname(userDto.getPersonalData().getNickname());
            game.setServerSeed(userDto.getData().getServerSeed());
            game.setClientSeed(userDto.getData().getClientSeed());
            game.setSalt(gameTowerSessionHash.getSalt());
            game.setTimestamp(System.currentTimeMillis());

            log.info("Saving an entity game on service {} method: handlePlayTower", GameTowerServiceImpl.class);

            gameRepository.save(game);

            GameMessage gameMessage = new GameMessage();

            gameMessage.setGameType(GameType.TOWER);
            gameMessage.setWin(gameTowerPlayResponse.getWin());
            gameMessage.setBet(gameTowerSessionHash.getBet());
            gameMessage.setCoefficient(gameTowerPlayResponse.getCoefficient());
            gameMessage.setUser(userDto);
            gameMessage.setTimestamp(game.getTimestamp());

            String jsonRequest = gson.toJson(gameMessage);

            rabbitTemplate.convertAndSend("game-queue", jsonRequest);

            return gameTowerPlayResponse;
        }
        gameTowerSessionHash.setCoefficient(gameTowerSessionHash.getCoefficient()*((double) 1 /((double) (5 - mines[0].length) /5)));
        gameTowerSessionHash.setWin((long)
                (gameTowerSessionHash.getBet() * (Math.floor((gameTowerSessionHash.getCoefficient() - gameTowerSessionHash.getCoefficient() * 0.02) * 1e2) / 1e2)));
        gameTowerSessionHash.setStep(gameTowerSessionHash.getStep() + 1);

        log.info("Saving a hash gameTowerSessionHash on service {} method: handlePlayTower", GameTowerServiceImpl.class);

        gameTowerSessionHashRepository.save(gameTowerSessionHash);

        gameTowerPlayResponse.setGameState(GameState.CONTINUE);
        gameTowerPlayResponse.setCoefficient(Math.floor((gameTowerSessionHash.getCoefficient() - gameTowerSessionHash.getCoefficient() * 0.02) * 1e2) / 1e2);
        gameTowerPlayResponse.setWin((long) (gameTowerSessionHash.getBet() * gameTowerPlayResponse.getCoefficient()));
        gameTowerPlayResponse.setMines(null);

        return gameTowerPlayResponse;
    }

    @Override
    public GameTowerPlayResponse handleBetTaking(String jwtToken) {
        log.info("Starting method handleBetTaking on service {} method: handleBetTaking", GameTowerServiceImpl.class);

        log.info("Receiving entity user by JWT token on service {} method: handleBetTaking", GameTowerServiceImpl.class);

        ResponseEntity<UserDto> userDtoResponseEntity =
                restTemplate.postForEntity(JWT_URL + "?jwtToken=" + jwtToken, null, UserDto.class);

        if (userDtoResponseEntity.getStatusCode().isError()){
            log.warn("Incorrect JWT token on service {} method: handleBetTaking", GameTowerServiceImpl.class);
            throw new InvalidCredentialsException("Incorrect JWT token");
        }

        UserDto userDto = userDtoResponseEntity.getBody();

        log.info("Finding gameTowerSessionHash hash by Username on service {} method: handleBetTaking", GameTowerServiceImpl.class);

        GameTowerSessionHash gameTowerSessionHash =
                gameTowerSessionHashRepository.findGameTowerSessionHashByUsername(userDto.getUsername());

        if (gameTowerSessionHash == null){
            log.warn("Game session is out of date, start a new one on service {} method: handleBetTaking", GameTowerServiceImpl.class);
            throw new InvalidCredentialsException("Game session is out of date, start a new one");
        }

        log.info("Creating a response for this method on service {} method: handleBetTaking", GameTowerServiceImpl.class);

        GameTowerPlayResponse gameTowerPlayResponse = new GameTowerPlayResponse();

        gameTowerPlayResponse.setGameState(GameState.WIN);
        gameTowerPlayResponse.setPicked(gameTowerSessionHash.getPicked());
        gameTowerPlayResponse.setMines(gson.fromJson(gameTowerSessionHash.getMines(), int[][].class));
        gameTowerPlayResponse.setCoefficient(Math.floor((gameTowerSessionHash.getCoefficient() - gameTowerSessionHash.getCoefficient() * 0.02) * 1e2) / 1e2);
        gameTowerPlayResponse.setWin((long) (gameTowerSessionHash.getBet() * gameTowerPlayResponse.getCoefficient()));

        log.info("Updating entity user balance by Id on service {} method: handleBetTaking", GameTowerServiceImpl.class);

        userRepository.updateBalanceById(userDto.getBalance() + (long) (gameTowerSessionHash.getBet() * gameTowerPlayResponse.getCoefficient()), userDto.getId());

        Game game = new Game();

        game.setBet(gameTowerSessionHash.getBet());
        game.setGameType(GameType.TOWER);
        game.setWin(gameTowerPlayResponse.getWin());
        game.setBet(gameTowerSessionHash.getBet());
        game.setCoefficient(gameTowerPlayResponse.getCoefficient());
        game.setUsername(userDto.getUsername());
        game.setNickname(userDto.getPersonalData().getNickname());
        game.setServerSeed(userDto.getData().getServerSeed());
        game.setClientSeed(userDto.getData().getClientSeed());
        game.setSalt(gameTowerSessionHash.getSalt());
        game.setTimestamp(System.currentTimeMillis());

        log.info("Saving an entity game on service {} method: handlePlayTower", GameTowerServiceImpl.class);

        gameRepository.save(game);

        GameMessage gameMessage = new GameMessage();

        gameMessage.setGameType(GameType.TOWER);
        gameMessage.setWin(gameTowerPlayResponse.getWin());
        gameMessage.setBet(gameTowerSessionHash.getBet());
        gameMessage.setCoefficient(gameTowerPlayResponse.getCoefficient());
        gameMessage.setUser(userDto);
        gameMessage.setTimestamp(game.getTimestamp());

        log.info("Deleting gameTowerSessionHash hash on service {} method: handleBetTaking", GameTowerServiceImpl.class);

        gameTowerSessionHashRepository.delete(gameTowerSessionHash);

        String jsonRequest = gson.toJson(gameMessage);

        rabbitTemplate.convertAndSend("game-queue", jsonRequest);

        return gameTowerPlayResponse;
    }

    @Override
    public GameTowerGetCurrentResponse getCurrentTowerGame(String jwtToken) {
        log.info("Starting method getCurrentTowerGame on service {} method: getCurrentTowerGame", GameTowerServiceImpl.class);

        log.info("Receiving entity user by JWT token on service {} method: getCurrentTowerGame", GameTowerServiceImpl.class);

        ResponseEntity<UserDto> userDtoResponseEntity =
                restTemplate.postForEntity(JWT_URL + "?jwtToken=" + jwtToken, null, UserDto.class);

        if (userDtoResponseEntity.getStatusCode().isError()){
            log.warn("Incorrect JWT token on service {} method: getCurrentTowerGame", GameTowerServiceImpl.class);
            throw new InvalidCredentialsException("Incorrect JWT token");
        }

        UserDto userDto = userDtoResponseEntity.getBody();

        GameTowerSessionHash gameTowerSessionHash =
                gameTowerSessionHashRepository.findGameTowerSessionHashByUsername(userDto.getUsername());

        if (gameTowerSessionHash == null){
            log.warn("Game session is out of date, start a new one on service {} method: getCurrentTowerGame",
                    GameTowerServiceImpl.class);
            throw new InvalidCredentialsException("Game session is out of date, start a new one");
        }

        log.info("Creating response for this method on service {} method: getCurrentTowerGame", GameTowerServiceImpl.class);

        GameTowerGetCurrentResponse gameTowerGetCurrentResponse = new GameTowerGetCurrentResponse();

        gameTowerGetCurrentResponse.setPicked(gameTowerSessionHash.getPicked());
        gameTowerGetCurrentResponse.setBet(gameTowerSessionHash.getBet());
        gameTowerGetCurrentResponse.setWin(gameTowerSessionHash.getWin());
        gameTowerGetCurrentResponse.setCoefficient(Math.floor((gameTowerSessionHash.getCoefficient() - gameTowerSessionHash.getCoefficient() * 0.02) * 1e2) / 1e2);
        gameTowerGetCurrentResponse.setMines(null);

        return gameTowerGetCurrentResponse;
    }

    @Override
    public Game getLastTowerGame(String jwtToken) {
        log.info("Starting method getLastTowerGame on service {} method: getLastTowerGame", GameTowerServiceImpl.class);

        log.info("Receiving entity user by JWT token on service {} method: getLastTowerGame", GameTowerServiceImpl.class);

        ResponseEntity<UserDto> userDtoResponseEntity =
                restTemplate.postForEntity(JWT_URL + "?jwtToken=" + jwtToken, null, UserDto.class);

        if (userDtoResponseEntity.getStatusCode().isError()){
            log.warn("Incorrect JWT token on service {} method: getLastTowerGame", GameTowerServiceImpl.class);
            throw new InvalidCredentialsException("Incorrect JWT token");
        }

        UserDto userDto = userDtoResponseEntity.getBody();

        log.info("Finding last Tower game by GameType on service {} method: getLastTowerGame", GameTowerServiceImpl.class);

        return gameRepository.findLastByIdAndGameType(userDto.getId(), GameType.TOWER);
    }
}
