package eu.panic.gamediceservice.template.service.implement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.panic.gamediceservice.template.dto.UserDto;
import eu.panic.gamediceservice.template.entity.Game;
import eu.panic.gamediceservice.template.enums.GameState;
import eu.panic.gamediceservice.template.enums.GameType;
import eu.panic.gamediceservice.template.exception.InsufficientFundsException;
import eu.panic.gamediceservice.template.exception.InvalidCredentialsException;
import eu.panic.gamediceservice.template.payload.GameDiceRequest;
import eu.panic.gamediceservice.template.payload.GameDiceResponse;
import eu.panic.gamediceservice.template.repository.implement.GameRepositoryImpl;
import eu.panic.gamediceservice.template.repository.implement.UserRepositoryImpl;
import eu.panic.gamediceservice.template.service.GameDiceService;
import eu.panic.gamediceservice.template.util.GameSessionUtil;
import eu.panic.gamediceservice.template.util.HexGeneratorUtil;
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
    public GameDiceResponse handlePlayDice(String jwtToken, GameDiceRequest gameDiceRequest) {
        log.info("Starting method handlePlayDice on service {} method: handlePlayDice", GameDiceServiceImpl.class);

        log.info("Receiving entity user by jwtToken on service {} method: handlePlayDice", GameDiceServiceImpl.class);

        ResponseEntity<UserDto> userDtoResponseEntity =
                restTemplate.postForEntity(JWT_URL + "?jwtToken=" + jwtToken, null, UserDto.class);

        if (userDtoResponseEntity.getStatusCode().isError()){
            log.warn("Incorrect JWT token on service {} method: handlePlayDice", GameDiceServiceImpl.class);
            throw new InvalidCredentialsException("Incorrect JWT token");
        }

        if (gameDiceRequest.getChance() < 1 || gameDiceRequest.getChance() > 95 || gameDiceRequest.getAmount() < 1){
            log.warn("Incorrect Dice data on service {} method: handlePlayDice", GameDiceServiceImpl.class);
            throw new InvalidCredentialsException("Incorrect Dice data");
        }

        UserDto userDto = userDtoResponseEntity.getBody();

        if (userDto.getBalance() < gameDiceRequest.getAmount()){
            log.warn("You do not have enough money for this bet on service {} method: handlePlayDice", GameDiceServiceImpl.class);
            throw new InsufficientFundsException("You do not have enough money for this bet");
        }

        log.info("Updating entity balance on service {} method: handlePlayDice", GameDiceServiceImpl.class);

        userRepository.updateBalanceById(userDto.getBalance() - gameDiceRequest.getAmount(), userDto.getId());

        double coefficient = Math.ceil((100 / gameDiceRequest.getChance() - 100 / gameDiceRequest.getChance() * 0.01) * 1e2) / 1e2;

        GameSessionUtil gameSession = new GameSessionUtil(userDto.getData().getServerSeed(), userDto.getData().getClientSeed());
        gameSession.generateSalt();

        double diceNumber = Math.floor(gameSession.generateRandomNumber() * 10001) / 100;

        log.info("Creating a new entity game on service {} method: handlePlayDice", GameDiceServiceImpl.class);

        Game game = new Game();

        game.setGameType(GameType.DICE);
        game.setUsername(userDto.getUsername());
        game.setNickname(userDto.getPersonalData().getNickname());
        game.setBet(gameDiceRequest.getAmount());
        game.setClientSeed(userDto.getData().getClientSeed());
        game.setServerSeed(userDto.getData().getServerSeed());
        game.setSalt(gameSession.getSalt());
        game.setTimestamp(System.currentTimeMillis());

        log.info("Creating response for this method on service {} method: handlePlayDice", GameDiceServiceImpl.class);

        GameDiceResponse gameDiceResponse = new GameDiceResponse();

        gameDiceResponse.setGameType(GameType.DICE);

        if (diceNumber > (100 - gameDiceRequest.getChance())){
            game.setCoefficient(coefficient);
            game.setWin((long) (gameDiceRequest.getAmount() * coefficient));

            gameDiceResponse.setGameState(GameState.WIN);
            gameDiceResponse.setAmount(game.getWin());

            userRepository.updateBalanceById(userDto.getBalance() + game.getWin(), userDto.getId());
        }else{
            game.setCoefficient(0D);
            game.setWin(0L);

            gameDiceResponse.setGameState(GameState.LOSE);
            gameDiceResponse.setAmount(0L);
        }

        log.info("Saving an entity game on service {} method: handlePlayDice", GameDiceServiceImpl.class);

        gameRepository.save(game);

        log.info("Creating jsonMessage message for game-queue on service {} method: handlePlayDice", GameDiceServiceImpl.class);

        String jsonMessage = null;
        try {
            jsonMessage = objectMapper.writeValueAsString(game);
        } catch (JsonProcessingException jsonProcessingException){
            jsonProcessingException.printStackTrace();
        }

        rabbitTemplate.convertAndSend("game-queue", jsonMessage);

        log.info("Updating user clientSeed and user serverSeed by Id on service {} method: handlePlayDice", GameDiceServiceImpl.class);

        userRepository.updateClientSeedAndServerSeedById(HexGeneratorUtil.generateHex(), HexGeneratorUtil.generateHex(), userDto.getId());

        return gameDiceResponse;
    }
}
