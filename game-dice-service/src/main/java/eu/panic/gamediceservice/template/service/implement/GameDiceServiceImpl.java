package eu.panic.gamediceservice.template.service.implement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.panic.gamediceservice.template.dto.UserDto;
import eu.panic.gamediceservice.template.entity.Game;
import eu.panic.gamediceservice.template.enums.GameState;
import eu.panic.gamediceservice.template.enums.GameType;
import eu.panic.gamediceservice.template.exception.InsufficientFundsException;
import eu.panic.gamediceservice.template.exception.InvalidCredentialsException;
import eu.panic.gamediceservice.template.payload.GameDicePlayRequest;
import eu.panic.gamediceservice.template.payload.GameDicePlayResponse;
import eu.panic.gamediceservice.template.payload.GameMessage;
import eu.panic.gamediceservice.template.repository.implement.GameRepositoryImpl;
import eu.panic.gamediceservice.template.repository.implement.UserRepositoryImpl;
import eu.panic.gamediceservice.template.service.GameDiceService;
import eu.panic.gamediceservice.template.util.GameSessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class GameDiceServiceImpl implements GameDiceService {
    public GameDiceServiceImpl(GameRepositoryImpl gameRepository, UserRepositoryImpl userRepository, RestTemplate restTemplate, RabbitTemplate rabbitTemplate) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
        this.rabbitTemplate = rabbitTemplate;
    }

    private final GameRepositoryImpl gameRepository;
    private final UserRepositoryImpl userRepository;
    private final RestTemplate restTemplate;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String JWT_URL = "http://localhost:8080/api/auth/getInfoByJwt";
    @Override
    public GameDicePlayResponse handlePlayDice(String jwtToken, GameDicePlayRequest gameDicePlayRequest) {
        log.info("Starting method handlePlayDice on service {} method: handlePlayDice", GameDiceServiceImpl.class);

        log.info("Receiving entity user by jwtToken on service {} method: handlePlayDice", GameDiceServiceImpl.class);

        ResponseEntity<UserDto> userDtoResponseEntity =
                restTemplate.postForEntity(JWT_URL + "?jwtToken=" + jwtToken, null, UserDto.class);

        if (userDtoResponseEntity.getStatusCode().isError()){
            log.warn("Incorrect JWT token on service {} method: handlePlayDice", GameDiceServiceImpl.class);
            throw new InvalidCredentialsException("Incorrect JWT token");
        }

        if (gameDicePlayRequest.getChance() < 1 || gameDicePlayRequest.getChance() > 95 || gameDicePlayRequest.getAmount() < 1){
            log.warn("Incorrect Dice data on service {} method: handlePlayDice", GameDiceServiceImpl.class);
            throw new InvalidCredentialsException("Incorrect Dice data");
        }

        UserDto userDto = userDtoResponseEntity.getBody();

        if (userDto.getBalance() < gameDicePlayRequest.getAmount()){
            log.warn("You do not have enough money for this bet on service {} method: handlePlayDice", GameDiceServiceImpl.class);
            throw new InsufficientFundsException("You do not have enough money for this bet");
        }

        log.info("Updating entity balance on service {} method: handlePlayDice", GameDiceServiceImpl.class);

        userRepository.updateBalanceById(userDto.getBalance() - gameDicePlayRequest.getAmount(), userDto.getId());

        double coefficient = Math.ceil((100 / gameDicePlayRequest.getChance() - 100 / gameDicePlayRequest.getChance() * 0.01) * 1e2) / 1e2;

        GameSessionUtil gameSession = new GameSessionUtil(userDto.getData().getServerSeed(), userDto.getData().getClientSeed());
        gameSession.generateSalt();

        double diceNumber = Math.floor(gameSession.generateRandomNumber() * 10001) / 100;

        log.info("Creating a new entity game on service {} method: handlePlayDice", GameDiceServiceImpl.class);

        Game game = new Game();

        game.setGameType(GameType.DICE);
        game.setUsername(userDto.getUsername());
        game.setNickname(userDto.getPersonalData().getNickname());
        game.setBet(gameDicePlayRequest.getAmount());
        game.setClientSeed(userDto.getData().getClientSeed());
        game.setServerSeed(userDto.getData().getServerSeed());
        game.setSalt(gameSession.getSalt());
        game.setTimestamp(System.currentTimeMillis());

        log.info("Creating response for this method on service {} method: handlePlayDice", GameDiceServiceImpl.class);

        GameDicePlayResponse gameDicePlayResponse = new GameDicePlayResponse();

        if (diceNumber > (100 - gameDicePlayRequest.getChance())){
            game.setCoefficient(coefficient);
            game.setWin((long) (gameDicePlayRequest.getAmount() * coefficient));

            gameDicePlayResponse.setGameState(GameState.WIN);
            gameDicePlayResponse.setAmount(game.getWin());

            userRepository.updateBalanceById((userDto.getBalance() - gameDicePlayRequest.getAmount()) + game.getWin(), userDto.getId());
        }else{
            game.setCoefficient(0D);
            game.setWin(0L);

            gameDicePlayResponse.setGameState(GameState.LOSE);
            gameDicePlayResponse.setAmount(0L);
        }

        log.info("Saving an entity game on service {} method: handlePlayDice", GameDiceServiceImpl.class);

        gameRepository.save(game);

        log.info("Creating jsonMessage message for game-queue on service {} method: handlePlayDice", GameDiceServiceImpl.class);

        GameMessage gameMessage = new GameMessage();

        gameMessage.setGameType(GameType.DICE);
        gameMessage.setUser(userDto);
        gameMessage.setBet(game.getBet());
        gameMessage.setWin(game.getWin());
        gameMessage.setCoefficient(game.getCoefficient());
        gameMessage.setTimestamp(game.getTimestamp());

        String jsonMessage = null;
        try {
            jsonMessage = objectMapper.writeValueAsString(gameMessage);
        } catch (JsonProcessingException jsonProcessingException){
            jsonProcessingException.printStackTrace();
        }

        rabbitTemplate.convertAndSend("game-queue", jsonMessage);

        return gameDicePlayResponse;
    }

    @Override
    public Game getLastDiceGame(String jwtToken) {
        log.info("Starting method getLastDiceGame on service {} method: getLastDiceGame", GameDiceServiceImpl.class);

        log.info("Receiving entity user by jwtToken on service {} method: getLastDiceGame", GameDiceServiceImpl.class);

        ResponseEntity<UserDto> userDtoResponseEntity =
                restTemplate.postForEntity(JWT_URL + "?jwtToken=" + jwtToken, null, UserDto.class);

        if (userDtoResponseEntity.getStatusCode().isError()){
            log.warn("Incorrect JWT token on service {} method: getLastDiceGame", GameDiceServiceImpl.class);
            throw new InvalidCredentialsException("Incorrect JWT token");
        }

        UserDto userDto = userDtoResponseEntity.getBody();

        return gameRepository.findGameByUsernameAndGameTypeOrderDesc(userDto.getUsername(), GameType.DICE);
    }
}
