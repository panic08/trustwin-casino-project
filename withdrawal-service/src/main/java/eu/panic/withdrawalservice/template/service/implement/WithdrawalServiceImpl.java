package eu.panic.withdrawalservice.template.service.implement;

import eu.panic.withdrawalservice.template.dto.UserDto;
import eu.panic.withdrawalservice.template.entity.Withdrawal;
import eu.panic.withdrawalservice.template.enums.WithdrawalStatus;
import eu.panic.withdrawalservice.template.exception.InsufficientFundsException;
import eu.panic.withdrawalservice.template.exception.InvalidCredentialsException;
import eu.panic.withdrawalservice.template.payload.CancelWithdrawalByIdRequest;
import eu.panic.withdrawalservice.template.payload.CancelWithdrawalByIdResponse;
import eu.panic.withdrawalservice.template.payload.CreateWithdrawalRequest;
import eu.panic.withdrawalservice.template.payload.CreateWithdrawalResponse;
import eu.panic.withdrawalservice.template.repository.UserRepository;
import eu.panic.withdrawalservice.template.repository.WithdrawalRepository;
import eu.panic.withdrawalservice.template.service.WithdrawalService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class WithdrawalServiceImpl implements WithdrawalService {
    public WithdrawalServiceImpl(WithdrawalRepository withdrawalRepository, UserRepository userRepository, RestTemplate restTemplate) {
        this.withdrawalRepository = withdrawalRepository;
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
    }

    private final WithdrawalRepository withdrawalRepository;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private static final String JWT_URL = "http://localhost:8080/api/auth/getInfoByJwt";
    @Override
    @Transactional(rollbackOn = Throwable.class)
    public CreateWithdrawalResponse createWithdrawal(String jwtToken, CreateWithdrawalRequest createWithdrawalRequest) {
        log.info("Starting method createWithdrawal on service {} method: createWithdrawal", WithdrawalServiceImpl.class);

        switch (createWithdrawalRequest.getMethod()){
            case BTC -> {
                if (createWithdrawalRequest.getAmount() < 200){
                    log.warn("The minimum bet for withdrawal in BTC is 200 coins on service {} method: createWithdrawal",
                            WithdrawalServiceImpl.class);
                    throw new InvalidCredentialsException("The minimum bet for withdrawal in BTC is 200 coins");
                }
            }
            case ETH -> {
                if (createWithdrawalRequest.getAmount() < 200){
                    log.warn("The minimum bet for withdrawal in ETH is 200 coins on service {} method: createWithdrawal",
                            WithdrawalServiceImpl.class);
                    throw new InvalidCredentialsException("The minimum bet for withdrawal in ETH is 200 coins");
                }
            }
            case LTC -> {
                if (createWithdrawalRequest.getAmount() < 200){
                    log.warn("The minimum bet for withdrawal in LTC is 200 coins on service {} method: createWithdrawal",
                            WithdrawalServiceImpl.class);
                    throw new InvalidCredentialsException("The minimum bet for withdrawal in LTC is 200 coins");
                }
            }
        }

        log.info("Receiving entity user by jwtToken on service {} method: createWithdrawal", WithdrawalServiceImpl.class);

        ResponseEntity<UserDto> userDtoResponseEntity =
                restTemplate.postForEntity(JWT_URL + "?jwtToken=" + jwtToken, null, UserDto.class);

        if (userDtoResponseEntity.getStatusCode().isError()){
            log.warn("Incorrect JWT token on service {} method: createWithdrawal", WithdrawalServiceImpl.class);
            throw new InvalidCredentialsException("Incorrect JWT token");
        }

        UserDto userDto = userDtoResponseEntity.getBody();

        if (!userDto.getIsAccountNonLocked()){
            log.warn("You have been temporarily blocked. For all questions contact support on service {} " +
                    "method: createWithdrawal", WithdrawalServiceImpl.class);
            throw new InvalidCredentialsException("You have been temporarily blocked. For all questions contact support");
        }

        if (userDto.getBalance() < createWithdrawalRequest.getAmount()){
            log.warn("Insufficient funds to withdraw on service {} method: createWithdrawal", WithdrawalServiceImpl.class);
            throw new InsufficientFundsException("Insufficient balance to withdraw funds");
        }
        log.info("Updating entity user balance by Username on service {} method: createWithdrawal", WithdrawalServiceImpl.class);

        userRepository.updateBalanceByUsername(userDto.getBalance() - createWithdrawalRequest.getAmount(), userDto.getUsername());

        log.info("Creating an entity withdrawal on service {} method: createWithdrawal", WithdrawalServiceImpl.class);

        Withdrawal withdrawal = new Withdrawal();

        withdrawal.setStatus(WithdrawalStatus.AWAIT);
        withdrawal.setMethod(createWithdrawalRequest.getMethod());
        withdrawal.setAmount(createWithdrawalRequest.getAmount());
        withdrawal.setWalletId(createWithdrawalRequest.getWalletId());
        withdrawal.setUsername(userDto.getUsername());
        withdrawal.setTimestamp(System.currentTimeMillis());

        log.info("Saving an entity withdrawal on service {} method: createWithdrawal", WithdrawalServiceImpl.class);

        withdrawalRepository.save(withdrawal);

        log.info("Creating response for this method on service {} method: createWithdrawal", WithdrawalServiceImpl.class);

        CreateWithdrawalResponse createWithdrawalResponse = new CreateWithdrawalResponse();

        createWithdrawalResponse.setStatus(withdrawal.getStatus());
        createWithdrawalResponse.setMethod(createWithdrawalRequest.getMethod());
        createWithdrawalResponse.setAmount(createWithdrawalRequest.getAmount());
        createWithdrawalResponse.setWalletId(createWithdrawalRequest.getWalletId());
        createWithdrawalResponse.setTimestamp(withdrawal.getTimestamp());

        return createWithdrawalResponse;
    }

    @Override
    public List<Withdrawal> getWithdrawalsByUsername(String jwtToken) {
        log.info("Starting method getWithdrawalsByUsername on service {} method: getWithdrawalsByUsername", WithdrawalServiceImpl.class);

        log.info("Receiving entity user by jwtToken on service {} method: getWithdrawalsByUsername", WithdrawalServiceImpl.class);

        ResponseEntity<UserDto> userDtoResponseEntity =
                restTemplate.postForEntity(JWT_URL + "?jwtToken=" + jwtToken, null, UserDto.class);

        if (userDtoResponseEntity.getStatusCode().isError()){
            throw new InvalidCredentialsException("Incorrect JWT token");
        }

        UserDto userDto = userDtoResponseEntity.getBody();

        return withdrawalRepository.findAllByUsername(userDto.getUsername());
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public CancelWithdrawalByIdResponse cancelWithdrawalById(String jwtToken, CancelWithdrawalByIdRequest cancelWithdrawalByIdRequest) {
        log.info("Starting method cancelWithdrawalById on service {} method: cancelWithdrawalById", WithdrawalServiceImpl.class);

        log.info("Receiving entity user by jwtToken on service {} method: cancelWithdrawalById", WithdrawalServiceImpl.class);

        ResponseEntity<UserDto> userDtoResponseEntity =
                restTemplate.postForEntity(JWT_URL + "?jwtToken=" + jwtToken, null, UserDto.class);

        if (userDtoResponseEntity.getStatusCode().isError()){
            throw new InvalidCredentialsException("Incorrect JWT token");
        }

        UserDto userDto = userDtoResponseEntity.getBody();

        log.info("Finding entity withdrawal by Id on service {} method: cancelWithdrawalById", WithdrawalServiceImpl.class);

        Withdrawal withdrawal = withdrawalRepository.findById(cancelWithdrawalByIdRequest.getId()).orElseThrow();

        if (!withdrawal.getUsername().equals(userDto.getUsername())){
            log.warn("You cannot cancel this withdrawal on service {} method: cancelWithdrawalById", WithdrawalServiceImpl.class);
            throw new InvalidCredentialsException("You cannot cancel this withdrawal");
        }

        if (withdrawal.getStatus().equals(WithdrawalStatus.CANCELED)){
            log.warn("This withdrawal has already been canceled on service {} method: cancelWithdrawalById", WithdrawalServiceImpl.class);
            throw new InvalidCredentialsException("This withdrawal has already been canceled");
        }

        withdrawal.setStatus(WithdrawalStatus.CANCELED);

        log.info("Saving an entity withdrawal on service {} method: cancelWithdrawalById", WithdrawalServiceImpl.class);

        withdrawalRepository.save(withdrawal);

        log.info("Updating balance of entity user on service {} method: cancelWithdrawalById", WithdrawalServiceImpl.class);

        userRepository.updateBalanceByUsername(userDto.getBalance() + withdrawal.getAmount(), userDto.getUsername());

        log.info("Creating response for this method on service {} method: cancelWithdrawalById", WithdrawalServiceImpl.class);

        CancelWithdrawalByIdResponse cancelWithdrawalByIdResponse = new CancelWithdrawalByIdResponse();

        cancelWithdrawalByIdResponse.setStatus(WithdrawalStatus.CANCELED);
        cancelWithdrawalByIdResponse.setMethod(withdrawal.getMethod());
        cancelWithdrawalByIdResponse.setWalletId(withdrawal.getWalletId());
        cancelWithdrawalByIdResponse.setAmount(withdrawal.getAmount());
        cancelWithdrawalByIdResponse.setTimestamp(withdrawal.getTimestamp());

        return cancelWithdrawalByIdResponse;
    }
}
