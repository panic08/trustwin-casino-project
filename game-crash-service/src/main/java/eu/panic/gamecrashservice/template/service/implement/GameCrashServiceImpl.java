package eu.panic.gamecrashservice.template.service.implement;

import eu.panic.gamecrashservice.template.dto.CrashState;
import eu.panic.gamecrashservice.template.dto.UserDto;
import eu.panic.gamecrashservice.template.exception.InsufficientFundsException;
import eu.panic.gamecrashservice.template.exception.InvalidCredentialsException;
import eu.panic.gamecrashservice.template.hash.CrashBetHash;
import eu.panic.gamecrashservice.template.payload.GameCrashPlayRequest;
import eu.panic.gamecrashservice.template.repository.CrashBetHashRepository;
import eu.panic.gamecrashservice.template.repository.implement.GameRepositoryImpl;
import eu.panic.gamecrashservice.template.repository.implement.UserRepositoryImpl;
import eu.panic.gamecrashservice.template.service.GameCrashService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class GameCrashServiceImpl implements GameCrashService {
    public GameCrashServiceImpl(GameRepositoryImpl gameRepository, UserRepositoryImpl userRepository, SimpMessagingTemplate simpMessagingTemplate, CrashBetHashRepository crashBetHashRepository, RestTemplate restTemplate) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.crashBetHashRepository = crashBetHashRepository;
        this.restTemplate = restTemplate;
    }

    private final GameRepositoryImpl gameRepository;
    private final UserRepositoryImpl userRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final CrashBetHashRepository crashBetHashRepository;
    private final RestTemplate restTemplate;
    private static String JWT_URL = "http://localhost:8080/api/auth/getInfoByJwt";
    private static CrashState crashState = new CrashState();
    @Override
    public void handlePlayCrash(String jwtToken, GameCrashPlayRequest gameCrashPlayRequest) {
        log.info("Starting method handlePlayCrash on service {} method: handlePlayCrash", GameCrashServiceImpl.class);

        log.info("Receiving entity user by jwtToken on service {} method: handlePlayCrash", GameCrashServiceImpl.class);

        ResponseEntity<UserDto> userDtoResponseEntity =
                restTemplate.postForEntity(JWT_URL + "?jwtToken=" + jwtToken, null, UserDto.class);

        if (userDtoResponseEntity.getStatusCode().isError()){
            log.warn("Incorrect JWT token on service {} method: handlePlayCrash", GameCrashServiceImpl.class);
            throw new InvalidCredentialsException("Incorrect JWT token");
        }

        if (gameCrashPlayRequest.getAmount() > 100000 || gameCrashPlayRequest.getAmount() < 1){
            log.warn("Incorrect Crash data on service {} method: handlePlayCrash", GameCrashServiceImpl.class);
            throw new InvalidCredentialsException("Incorrect Crash data");
        }

        UserDto userDto = userDtoResponseEntity.getBody();

        if (userDto.getBalance() < gameCrashPlayRequest.getAmount()){
            log.warn("You do not have enough money for this bet on service {} method: handlePlayCrash", GameCrashServiceImpl.class);
            throw new InsufficientFundsException("You do not have enough money for this bet");
        }

        if (crashState.getIsStarted()){
            log.warn("You cannot place a bet as the game has already started on service {} method: handlePlayCrash", GameCrashServiceImpl.class);
            throw new InvalidCredentialsException("You cannot place a bet as the game has already started");
        }

        if (crashBetHashRepository.findCrashBetHashByUsername(userDto.getUsername()) != null){
            log.warn("You have already placed your bet, wait for the game to end on service {} method: handlePlayCrash", GameCrashServiceImpl.class);
            throw new InvalidCredentialsException("You have already placed your bet, wait for the game to end");
        }

        log.info("Creating new hash crashBetHash on service {} method: handlePlayCrash", GameCrashServiceImpl.class);

        CrashBetHash crashBetHash = new CrashBetHash();

        crashBetHash.setUsername(userDto.getUsername());
        crashBetHash.setBet(gameCrashPlayRequest.getAmount());
        crashBetHash.setIsTaken(false);
        crashBetHash.setWin(gameCrashPlayRequest.getAmount());
        crashBetHash.setCoefficient(1.00);
        crashBetHash.setTimestamp(System.currentTimeMillis());

        log.info("Saving a hash crashBetHash on service {} method: handlePlayCrash", GameCrashServiceImpl.class);

        crashBetHashRepository.save(crashBetHash);

    }
}
