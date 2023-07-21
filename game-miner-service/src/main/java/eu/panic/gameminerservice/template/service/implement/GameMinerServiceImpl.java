package eu.panic.gameminerservice.template.service.implement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.panic.gameminerservice.template.dto.UserDto;
import eu.panic.gameminerservice.template.entity.Game;
import eu.panic.gameminerservice.template.enums.GameState;
import eu.panic.gameminerservice.template.enums.GameType;
import eu.panic.gameminerservice.template.exception.InsufficientFundsException;
import eu.panic.gameminerservice.template.exception.InvalidCredentialsException;
import eu.panic.gameminerservice.template.hash.GameMinerSessionHash;
import eu.panic.gameminerservice.template.payload.*;
import eu.panic.gameminerservice.template.repository.MinerSessionHashRepository;
import eu.panic.gameminerservice.template.repository.implement.GameRepositoryImpl;
import eu.panic.gameminerservice.template.repository.implement.UserRepositoryImpl;
import eu.panic.gameminerservice.template.service.GameMinerService;
import eu.panic.gameminerservice.template.util.GameMinerUtil;
import eu.panic.gameminerservice.template.util.GameSessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
@Slf4j
public class GameMinerServiceImpl implements GameMinerService {
    public GameMinerServiceImpl(RestTemplate restTemplate, MinerSessionHashRepository minerSessionHashRepository, UserRepositoryImpl userRepository, GameRepositoryImpl gameRepository, RabbitTemplate rabbitTemplate) {
        this.restTemplate = restTemplate;
        this.minerSessionHashRepository = minerSessionHashRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    private final RestTemplate restTemplate;
    private final MinerSessionHashRepository minerSessionHashRepository;
    private final UserRepositoryImpl userRepository;
    private final GameRepositoryImpl gameRepository;
    private final RabbitTemplate rabbitTemplate;
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static final String JWT_URL = "http://localhost:8080/api/auth/getInfoByJwt";
    @Override
    public GameMinerCreateResponse handleCreatingMinerSession(String jwtToken, GameMinerCreateRequest gameMinerCreateRequest) {
        log.info("Starting method handleCreatingMinerSession on service {} method: handleCreatingMinerSession", GameMinerServiceImpl.class);

        log.info("Receiving entity user by JWT token on service {} method: handleCreatingMinerSession", GameMinerServiceImpl.class);

        ResponseEntity<UserDto> userDtoResponseEntity =
                restTemplate.postForEntity(JWT_URL + "?jwtToken=" + jwtToken, null, UserDto.class);

        if (userDtoResponseEntity.getStatusCode().isError()){
            log.warn("Incorrect JWT token on service {} method: handleCreatingMinerSession", GameMinerServiceImpl.class);
            throw new InvalidCredentialsException("Incorrect JWT token");
        }

        if (gameMinerCreateRequest.getBet() < 1 || gameMinerCreateRequest.getBet() > 100000
                || gameMinerCreateRequest.getMinesCount() < 2 || gameMinerCreateRequest.getMinesCount() > 24){
            log.warn("Incorrect Miner data on service {} method: handleCreatingMinerSession", GameMinerServiceImpl.class);
            throw new InvalidCredentialsException("Incorrect Miner data");
        }

        UserDto userDto = userDtoResponseEntity.getBody();

        log.info("Checking for hash existence on service {} method: handleCreatingMinerSession", GameMinerServiceImpl.class);

        if (minerSessionHashRepository.findMinerSessionHashByUsername(userDto.getUsername()) != null){
            log.warn("Finish the previous game session before starting a new one on service {} method: handleCreatingMinerSession", GameMinerServiceImpl.class);
            throw new InvalidCredentialsException("Finish the previous game session before starting a new one");
        }

        if (userDto.getBalance() < gameMinerCreateRequest.getBet()){
            log.warn("You do not have enough money for this bet on service {} method: handleCreatingMinerSession", GameMinerServiceImpl.class);
            throw new InsufficientFundsException("You do not have enough money for this bet");
        }

        GameSessionUtil gameSession = new GameSessionUtil(userDto.getData().getServerSeed(), userDto.getData().getClientSeed());

        double[] mineNumbers = new double[25];
        StringBuilder salt = new StringBuilder();

        for (int i = 0; i < 25; i++){
            gameSession.generateSalt();

            mineNumbers[i] = gameSession.generateRandomNumber();
        }

        salt.append(gameSession.getSalt());

        log.info("Updating entity user balance on service {} method: handleCreatingMinerSession", GameMinerServiceImpl.class);

        userRepository.updateBalanceById(userDto.getBalance() - gameMinerCreateRequest.getBet(), userDto.getId());

        GameMinerSessionHash gameMinerSessionHash = new GameMinerSessionHash();

        gameMinerSessionHash.setUsername(userDto.getUsername());
        gameMinerSessionHash.setBet(gameMinerCreateRequest.getBet());
        gameMinerSessionHash.setWin(gameMinerCreateRequest.getBet());
        gameMinerSessionHash.setCoefficient(1.00);
        gameMinerSessionHash.setPicked(new ArrayList<>());
        gameMinerSessionHash.setSalt(salt.toString());
        gameMinerSessionHash.setMines(GameMinerUtil.minesShuffling(mineNumbers, gameMinerCreateRequest.getMinesCount()));
        gameMinerSessionHash.setNotPicked(GameMinerUtil.findMissingNumbers(gameMinerSessionHash.getMines()));
        gameMinerSessionHash.setTimestamp(System.currentTimeMillis());

        log.info("Saving hash minerSessionHash on service {} method: handleCreatingMinerSession", GameMinerServiceImpl.class);

        minerSessionHashRepository.save(gameMinerSessionHash);

        log.info("Creating a response for this method on service {} method: handleCreatingMinerSession", GameMinerServiceImpl.class);

        GameMinerCreateResponse gameMinerCreateResponse = new GameMinerCreateResponse();

        gameMinerCreateResponse.setWin(gameMinerCreateRequest.getBet());
        gameMinerCreateResponse.setPicked(gameMinerSessionHash.getPicked());
        gameMinerCreateResponse.setCoefficient(1.00);

        return gameMinerCreateResponse;
    }

    @Override
    public GameMinerPlayResponse handlePlayMiner(String jwtToken, GameMinerPlayRequest gameMinerPlayRequest) {
        log.info("Starting method handlePlayMiner on service {} method: handlePlayMiner", GameMinerServiceImpl.class);

        log.info("Receiving entity user by JWT token on service {} method: handlePlayMiner", GameMinerServiceImpl.class);

        ResponseEntity<UserDto> userDtoResponseEntity =
                restTemplate.postForEntity(JWT_URL + "?jwtToken=" + jwtToken, null, UserDto.class);

        if (userDtoResponseEntity.getStatusCode().isError()){
            log.warn("Incorrect JWT token on service {} method: handlePlayMiner", GameMinerServiceImpl.class);
            throw new InvalidCredentialsException("Incorrect JWT token");
        }

        if (gameMinerPlayRequest.getPick() < 0 || gameMinerPlayRequest.getPick() > 24){
            log.warn("Incorrect Miner data on service {} method: handlePlayMiner", GameMinerServiceImpl.class);
            throw new InvalidCredentialsException("Incorrect Miner data");
        }

        GameMinerSessionHash gameMinerSessionHash = minerSessionHashRepository.findMinerSessionHashByUsername(userDtoResponseEntity.getBody().getUsername());

        if (gameMinerSessionHash == null) {
            log.warn("Game session is out of date, start a new one on service {} method: handlePlayMiner", GameMinerServiceImpl.class);
            throw new InvalidCredentialsException("Game session is out of date, start a new one");
        }

        UserDto userDto = userDtoResponseEntity.getBody();

        if (gameMinerSessionHash.getPicked() == null){
            gameMinerSessionHash.setPicked(new ArrayList<>());
        }
        for (Integer key : gameMinerSessionHash.getPicked()){
            if (key.equals(gameMinerPlayRequest.getPick())){
                log.warn("This index is already present in the list on service {} method: handlePlayMiner", GameMinerServiceImpl.class);
                throw new InvalidCredentialsException("This index is already present in the list");
            }
        }
        gameMinerSessionHash.getPicked().add(gameMinerPlayRequest.getPick());

        log.info("Creating response for this method on service {} method: handlePlayMiner", GameMinerServiceImpl.class);

        GameMinerPlayResponse gameMinerPlayResponse = new GameMinerPlayResponse();

        gameMinerPlayResponse.setPicked(gameMinerSessionHash.getPicked());

        for (Integer key : gameMinerSessionHash.getPicked()){
            for (Integer key1 : gameMinerSessionHash.getMines()){
                if (key.equals(key1)){
                    gameMinerPlayResponse.setGameState(GameState.LOSE);
                    gameMinerPlayResponse.setMines(gameMinerSessionHash.getMines());
                    gameMinerPlayResponse.setAmount(0);
                    gameMinerPlayResponse.setCoefficient(0.0);

                    Game game = new Game();

                    game.setGameType(GameType.MINER);
                    game.setUsername(userDto.getUsername());
                    game.setNickname(userDto.getPersonalData().getNickname());
                    game.setBet(gameMinerSessionHash.getBet());
                    game.setWin(0L);
                    game.setSalt(gameMinerSessionHash.getSalt());
                    game.setClientSeed(userDto.getData().getClientSeed());
                    game.setServerSeed(userDto.getData().getServerSeed());
                    game.setCoefficient(0D);
                    game.setTimestamp(System.currentTimeMillis());

                    log.info("Deleting minerSessionHash hash on service {} method: handlePlayMiner", GameMinerServiceImpl.class);

                    minerSessionHashRepository.delete(gameMinerSessionHash);

                    log.info("Saving an entity game on service {} method: handlePlayMiner", GameMinerServiceImpl.class);

                    gameRepository.save(game);

                    log.info("Creating jsonMessage message for game-queue on service {} method: handlePlayMiner", GameMinerServiceImpl.class);

                    GameMessage gameMessage = new GameMessage();

                    gameMessage.setGameType(GameType.MINER);
                    gameMessage.setUser(userDto);
                    gameMessage.setBet(gameMinerSessionHash.getBet());
                    gameMessage.setWin(0L);
                    gameMessage.setCoefficient(0D);
                    gameMessage.setTimestamp(System.currentTimeMillis());

                    String jsonMessage = null;

                    try {
                        jsonMessage = objectMapper.writeValueAsString(gameMessage);
                    }catch (JsonProcessingException jsonProcessingException){
                        jsonProcessingException.printStackTrace();
                    }

                     rabbitTemplate.convertAndSend("game-queue", jsonMessage);

                    return gameMinerPlayResponse;
                }
            }
        }
        double coefficient = gameMinerSessionHash.getCoefficient() * ((double) 1 / ((double) (25 - gameMinerSessionHash.getPicked().size() + 1 - gameMinerSessionHash.getMines().size()) / (25 - gameMinerSessionHash.getPicked().size() + 1)));
        gameMinerSessionHash.setCoefficient(coefficient);
        gameMinerSessionHash.setWin((long) (gameMinerSessionHash.getBet() * Math.floor((gameMinerSessionHash.getCoefficient() - (gameMinerSessionHash.getCoefficient() * 0.03)) * 1e2) / 1e2));

        gameMinerSessionHash.getNotPicked().remove(Integer.valueOf(gameMinerPlayRequest.getPick()));

        if (gameMinerSessionHash.getNotPicked().isEmpty()){
            gameMinerPlayResponse.setGameState(GameState.WIN);
            gameMinerPlayResponse.setCoefficient(gameMinerSessionHash.getCoefficient());
            gameMinerPlayResponse.setAmount(gameMinerSessionHash.getWin());

            Game game = new Game();

            game.setGameType(GameType.MINER);
            game.setUsername(userDto.getUsername());
            game.setNickname(userDto.getPersonalData().getNickname());
            game.setBet(gameMinerSessionHash.getBet());
            game.setWin(gameMinerSessionHash.getWin());
            game.setSalt(gameMinerSessionHash.getSalt());
            game.setClientSeed(userDto.getData().getClientSeed());
            game.setServerSeed(userDto.getData().getServerSeed());
            game.setCoefficient(Math.floor((gameMinerSessionHash.getCoefficient() - (gameMinerSessionHash.getCoefficient() * 0.03)) * 1e2) / 1e2);
            game.setTimestamp(System.currentTimeMillis());

            log.info("Deleting minerSessionHash hash on service {} method: handlePlayMiner", GameMinerServiceImpl.class);

            minerSessionHashRepository.delete(gameMinerSessionHash);

            log.info("Updating entity user Balance by Id on service {} method: handlePlayMiner", GameMinerServiceImpl.class);
            userRepository.updateBalanceById(userDto.getBalance() + gameMinerSessionHash.getWin(), userDto.getId());

            log.info("Saving an entity game on service {} method: handlePlayMiner", GameMinerServiceImpl.class);

            log.info("Creating jsonMessage message for game-queue on service {} method: handlePlayMiner", GameMinerServiceImpl.class);

            GameMessage gameMessage = new GameMessage();

            gameMessage.setGameType(GameType.MINER);
            gameMessage.setUser(userDto);
            gameMessage.setBet(gameMinerSessionHash.getBet());
            gameMessage.setWin(gameMinerSessionHash.getWin());
            gameMessage.setCoefficient(Math.floor((gameMinerSessionHash.getCoefficient() - (gameMinerSessionHash.getCoefficient() * 0.03)) * 1e2) / 1e2);
            gameMessage.setTimestamp(System.currentTimeMillis());

            String jsonMessage = null;

            try {
                jsonMessage = objectMapper.writeValueAsString(gameMessage);
            }catch (JsonProcessingException jsonProcessingException){
                jsonProcessingException.printStackTrace();
            }

            rabbitTemplate.convertAndSend("game-queue", jsonMessage);

            gameRepository.save(game);
        }

        gameMinerPlayResponse.setGameState(GameState.CONTINUE);
        gameMinerPlayResponse.setCoefficient(Math.floor((gameMinerSessionHash.getCoefficient() - (gameMinerSessionHash.getCoefficient() * 0.03)) * 1e2) / 1e2);
        gameMinerPlayResponse.setAmount(gameMinerSessionHash.getWin());

        log.info("Saving a minerSessionHash hash on service {} method: handlePlayMiner", GameMinerServiceImpl.class);

        minerSessionHashRepository.save(gameMinerSessionHash);
        return gameMinerPlayResponse;
    }

    @Override
    public GameMinerPlayResponse handleBetTaking(String jwtToken) {
        log.info("Starting method handleBetTaking on service {} method: handleBetTaking", GameMinerServiceImpl.class);

        log.info("Receiving entity user by JWT token on service {} method: handleBetTaking", GameMinerServiceImpl.class);

        ResponseEntity<UserDto> userDtoResponseEntity =
                restTemplate.postForEntity(JWT_URL + "?jwtToken=" + jwtToken, null, UserDto.class);

        if (userDtoResponseEntity.getStatusCode().isError()){
            log.warn("Incorrect JWT token on service {} method: handleBetTaking", GameMinerServiceImpl.class);
            throw new InvalidCredentialsException("Incorrect JWT token");
        }

        UserDto userDto = userDtoResponseEntity.getBody();

        log.info("Finding minerSessionHash hash by Username on service {} method: handleBetTaking", GameMinerServiceImpl.class);

        GameMinerSessionHash gameMinerSessionHash = minerSessionHashRepository.findMinerSessionHashByUsername(userDto.getUsername());

        if (gameMinerSessionHash == null) {
            log.warn("Game session is out of date, start a new one on service {} method: handleBetTaking", GameMinerServiceImpl.class);
            throw new InvalidCredentialsException("Game session is out of date, start a new one");
        }

        log.info("Updating entity user Balance by Id on service {} method: handleBetTaking", GameMinerServiceImpl.class);

        userRepository.updateBalanceById(userDto.getBalance() + gameMinerSessionHash.getWin(), userDto.getId());

        Game game = new Game();

        game.setGameType(GameType.MINER);
        game.setUsername(userDto.getUsername());
        game.setNickname(userDto.getPersonalData().getNickname());
        game.setBet(gameMinerSessionHash.getBet());
        game.setWin(gameMinerSessionHash.getWin());
        game.setSalt(gameMinerSessionHash.getSalt());
        game.setClientSeed(userDto.getData().getClientSeed());
        game.setServerSeed(userDto.getData().getServerSeed());
        game.setCoefficient(Math.floor((gameMinerSessionHash.getCoefficient() - (gameMinerSessionHash.getCoefficient() * 0.03)) * 1e2) / 1e2);
        game.setTimestamp(System.currentTimeMillis());

        log.info("Saving an entity game on service {} method: handleBetTaking", GameMinerServiceImpl.class);

        gameRepository.save(game);

        log.info("Deleting minerSessionHash hash on service {} method: handleBetTaking", GameMinerServiceImpl.class);

        minerSessionHashRepository.delete(gameMinerSessionHash);

        log.info("Creating jsonMessage message for game-queue on service {} method: handlePlayMiner", GameMinerServiceImpl.class);

        GameMessage gameMessage = new GameMessage();

        gameMessage.setGameType(GameType.MINER);
        gameMessage.setUser(userDto);
        gameMessage.setBet(gameMinerSessionHash.getBet());
        gameMessage.setWin(gameMinerSessionHash.getWin());
        gameMessage.setCoefficient(Math.floor((gameMinerSessionHash.getCoefficient() - (gameMinerSessionHash.getCoefficient() * 0.03)) * 1e2) / 1e2);
        gameMessage.setTimestamp(System.currentTimeMillis());

        String jsonMessage = null;

        try {
            jsonMessage = objectMapper.writeValueAsString(gameMessage);
        }catch (JsonProcessingException jsonProcessingException){
            jsonProcessingException.printStackTrace();
        }

        rabbitTemplate.convertAndSend("game-queue", jsonMessage);

        log.info("Creating response for this method on service {} method: handleBetTaking", GameMinerServiceImpl.class);

        GameMinerPlayResponse gameMinerPlayResponse = new GameMinerPlayResponse();

        gameMinerPlayResponse.setGameState(GameState.WIN);
        gameMinerPlayResponse.setPicked(gameMinerSessionHash.getPicked());
        gameMinerPlayResponse.setMines(gameMinerSessionHash.getMines());
        gameMinerPlayResponse.setCoefficient(Math.floor((gameMinerSessionHash.getCoefficient() - (gameMinerSessionHash.getCoefficient() * 0.03)) * 1e2) / 1e2);
        gameMinerPlayResponse.setAmount(gameMinerSessionHash.getWin());

        return gameMinerPlayResponse;
    }

    @Override
    public GameMinerSessionHash getCurrentMinerGame(String jwtToken) {
        log.info("Starting method getCurrentMinerGame on service {} method: getCurrentMinerGame", GameMinerServiceImpl.class);

        log.info("Receiving entity user by JWT token on service {} method: getCurrentMinerGame", GameMinerServiceImpl.class);

        ResponseEntity<UserDto> userDtoResponseEntity =
                restTemplate.postForEntity(JWT_URL + "?jwtToken=" + jwtToken, null, UserDto.class);

        if (userDtoResponseEntity.getStatusCode().isError()){
            log.warn("Incorrect JWT token on service {} method: getCurrentMinerGame", GameMinerServiceImpl.class);
            throw new InvalidCredentialsException("Incorrect JWT token");
        }

        UserDto userDto = userDtoResponseEntity.getBody();

        log.info("Finding minerSessionHash hash by Username on service {} method: getCurrentMinerGame", GameMinerServiceImpl.class);

        GameMinerSessionHash gameMinerSessionHash = minerSessionHashRepository.findMinerSessionHashByUsername(userDto.getUsername());

        if (gameMinerSessionHash == null) {
            log.warn("Game session is out of date, start a new one on service {} method: getCurrentMinerGame", GameMinerServiceImpl.class);
            throw new InvalidCredentialsException("Game session is out of date, start a new one");
        }

        gameMinerSessionHash.setCoefficient(Math.floor((gameMinerSessionHash.getCoefficient() - (gameMinerSessionHash.getCoefficient() * 0.03)) * 1e2) / 1e2);

        return gameMinerSessionHash;
    }

    @Override
    public Game getLastMinerGame(String jwtToken) {
        log.info("Starting method getLastMinerGame on service {} method: getLastMinerGame", GameMinerServiceImpl.class);

        log.info("Receiving entity user by JWT token on service {} method: getLastMinerGame", GameMinerServiceImpl.class);

        ResponseEntity<UserDto> userDtoResponseEntity =
                restTemplate.postForEntity(JWT_URL + "?jwtToken=" + jwtToken, null, UserDto.class);

        if (userDtoResponseEntity.getStatusCode().isError()){
            log.warn("Incorrect JWT token on service {} method: getLastMinerGame", GameMinerServiceImpl.class);
            throw new InvalidCredentialsException("Incorrect JWT token");
        }

        UserDto userDto = userDtoResponseEntity.getBody();

        return gameRepository.findGameByUsernameAndGameTypeOrderDesc(userDto.getUsername(), GameType.MINER);
    }
}
