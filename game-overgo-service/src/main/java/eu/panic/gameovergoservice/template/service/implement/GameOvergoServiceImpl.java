package eu.panic.gameovergoservice.template.service.implement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.panic.gameovergoservice.template.dto.UserDto;
import eu.panic.gameovergoservice.template.entity.Game;
import eu.panic.gameovergoservice.template.enums.GameState;
import eu.panic.gameovergoservice.template.enums.GameType;
import eu.panic.gameovergoservice.template.exception.InsufficientFundsException;
import eu.panic.gameovergoservice.template.exception.InvalidCredentialsException;
import eu.panic.gameovergoservice.template.payload.GameMessage;
import eu.panic.gameovergoservice.template.payload.GameOvergoPlayRequest;
import eu.panic.gameovergoservice.template.payload.GameOvergoPlayResponse;
import eu.panic.gameovergoservice.template.repository.implement.GameRepositoryImpl;
import eu.panic.gameovergoservice.template.repository.implement.UserRepositoryImpl;
import eu.panic.gameovergoservice.template.service.GameOvergoService;
import eu.panic.gameovergoservice.template.util.GameSessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class GameOvergoServiceImpl implements GameOvergoService {
    public GameOvergoServiceImpl(RestTemplate restTemplate, GameRepositoryImpl gameRepository, UserRepositoryImpl userRepository, RabbitTemplate rabbitTemplate) {
        this.restTemplate = restTemplate;
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    private final RestTemplate restTemplate;
    private final GameRepositoryImpl gameRepository;
    private final UserRepositoryImpl userRepository;
    private static final String JWT_URL = "http://localhost:8080/api/auth/getInfoByJwt";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RabbitTemplate rabbitTemplate;
    @Override
    public GameOvergoPlayResponse handlePlayOvergo(String jwtToken, GameOvergoPlayRequest gameOvergoPlayRequest) {
        log.info("Starting method handlePlayOvergo on service {} method: handlePlayOvergo", GameOvergoServiceImpl.class);

        log.info("Receiving entity user by jwtToken on service {} method: handlePlayOvergo", GameOvergoServiceImpl.class);

        ResponseEntity<UserDto> userDtoResponseEntity =
                restTemplate.postForEntity(JWT_URL + "?jwtToken=" + jwtToken, null, UserDto.class);

        if (userDtoResponseEntity.getStatusCode().isError()){
            log.warn("Incorrect JWT token on service {} method: handlePlayOvergo", GameOvergoServiceImpl.class);
            throw new InvalidCredentialsException("Incorrect JWT token");
        }

        if (gameOvergoPlayRequest.getBet() < 1 || gameOvergoPlayRequest.getChance() < 0.001 || gameOvergoPlayRequest.getChance() > 95.098){
            log.warn("Incorrect Overgo data on service {} method: handlePlayOvergo", GameOvergoServiceImpl.class);
            throw new InvalidCredentialsException("Incorrect Overgo data");
        }

        UserDto userDto = userDtoResponseEntity.getBody();

        if (userDto.getBalance() < gameOvergoPlayRequest.getBet()){
            log.warn("You do not have enough money for this bet on service {} method: handlePlayOvergo", GameOvergoServiceImpl.class);
            throw new InsufficientFundsException("You do not have enough money for this bet");
        }

        log.info("Updating entity balance on service {} method: handlePlayOvergo", GameOvergoServiceImpl.class);

        userRepository.updateBalanceById(userDto.getBalance() - gameOvergoPlayRequest.getBet(), userDto.getId());

        double coefficient = Math.ceil((100 / gameOvergoPlayRequest.getChance() - 100 / gameOvergoPlayRequest.getChance() * 0.03) * 1e2) / 1e2;

        GameSessionUtil gameSession = new GameSessionUtil(userDto.getData().getServerSeed(), userDto.getData().getClientSeed());

        gameSession.generateSalt();

        double overgoNumber = Math.ceil(Math.max(1, 1000000 / (Math.floor(gameSession.generateRandomNumber() * 1000000) + 1) * (1 - 0.03)) * 1e2) / 1e2;

        Game game = new Game();

        game.setGameType(GameType.OVERGO);
        game.setUsername(userDto.getUsername());
        game.setNickname(userDto.getPersonalData().getNickname());
        game.setBet(gameOvergoPlayRequest.getBet());
        game.setClientSeed(userDto.getData().getClientSeed());
        game.setServerSeed(userDto.getData().getServerSeed());
        game.setSalt(gameSession.getSalt());
        game.setTimestamp(System.currentTimeMillis());

        log.info("Creating response for this method on service {} method: handlePlayOvergo", GameOvergoServiceImpl.class);

        GameOvergoPlayResponse gameOvergoPlayResponse = new GameOvergoPlayResponse();

        gameOvergoPlayResponse.setMaxCoefficient(overgoNumber);

        if (coefficient < overgoNumber){
            game.setWin((long) (game.getBet() * coefficient));
            game.setCoefficient(coefficient);

            gameOvergoPlayResponse.setGameState(GameState.WIN);
            gameOvergoPlayResponse.setWin(game.getWin());

            userRepository.updateBalanceById((userDto.getBalance() - gameOvergoPlayRequest.getBet()) + game.getWin(), userDto.getId());
        }else{
            game.setWin(0L);
            game.setCoefficient(0D);

            gameOvergoPlayResponse.setGameState(GameState.LOSE);
            gameOvergoPlayResponse.setWin(game.getWin());
        }

        log.info("Saving an entity game on service {} method: handlePlayOvergo", GameOvergoServiceImpl.class);

        gameRepository.save(game);

        log.info("Creating jsonMessage message for game-queue on service {} method: handlePlayDice", GameOvergoServiceImpl.class);

        GameMessage gameMessage = new GameMessage();

        gameMessage.setGameType(GameType.OVERGO);
        gameMessage.setUser(userDto);
        gameMessage.setBet(game.getBet());
        gameMessage.setWin(game.getWin());
        gameMessage.setCoefficient(game.getCoefficient());
        gameMessage.setTimestamp(game.getTimestamp());

        String jsonMessage = null;

        try {
            jsonMessage = objectMapper.writeValueAsString(gameMessage);
        }catch (JsonProcessingException jsonProcessingException){
            jsonProcessingException.printStackTrace();
        }

        rabbitTemplate.convertAndSend("game-queue", jsonMessage);
        return gameOvergoPlayResponse;
    }
    @Override
    public Game getLastOvergoGame(String jwtToken) {
        log.info("Starting method getLastOvergoGame on service {} method: getLastOvergoGame", GameOvergoServiceImpl.class);

        log.info("Receiving entity user by jwtToken on service {} method: getLastOvergoGame", GameOvergoServiceImpl.class);

        ResponseEntity<UserDto> userDtoResponseEntity =
                restTemplate.postForEntity(JWT_URL + "?jwtToken=" + jwtToken, null, UserDto.class);

        if (userDtoResponseEntity.getStatusCode().isError()){
            log.warn("Incorrect JWT token on service {} method: getLastOvergoGame", GameOvergoServiceImpl.class);
            throw new InvalidCredentialsException("Incorrect JWT token");
        }

        UserDto userDto = userDtoResponseEntity.getBody();

        return gameRepository.findGameByUsernameAndGameTypeOrderDesc(userDto.getUsername(), GameType.OVERGO);
    }
}
