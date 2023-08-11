package eu.panic.adminservice.template.service.implement;

import eu.panic.adminservice.template.dto.UserDto;
import eu.panic.adminservice.template.entity.Withdrawal;
import eu.panic.adminservice.template.enums.Role;
import eu.panic.adminservice.template.enums.WithdrawalStatus;
import eu.panic.adminservice.template.exception.InvalidCredentialsException;
import eu.panic.adminservice.template.repository.UserRepository;
import eu.panic.adminservice.template.repository.WithdrawalRepository;
import eu.panic.adminservice.template.service.AdminService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {
    public AdminServiceImpl(WithdrawalRepository withdrawalRepository, UserRepository userRepository, RestTemplate restTemplate) {
        this.withdrawalRepository = withdrawalRepository;
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
    }

    private final WithdrawalRepository withdrawalRepository;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private static final String JWT_URL = "http://localhost:8080/api/auth/getInfoByJwt";

    @Override
    public List<Withdrawal> getAllWithdrawalsByWithdrawalStatus(String jwtToken, WithdrawalStatus withdrawalStatus) {
        log.info("Starting method getAllWithdrawalsByWithdrawalStatus on service {} method: getAllWithdrawalsByWithdrawalStatus", AdminServiceImpl.class);

        log.info("Receiving entity user by JWT token on service {} method: getAllWithdrawalsByWithdrawalStatus", AdminServiceImpl.class);

        ResponseEntity<UserDto> userDtoResponseEntity =
                restTemplate.postForEntity(JWT_URL + "?jwtToken=" + jwtToken, null, UserDto.class);

        if (userDtoResponseEntity.getStatusCode().isError()){
            log.warn("Incorrect JWT token on service {} method: getAllWithdrawalsByWithdrawalStatus", AdminServiceImpl.class);
            throw new InvalidCredentialsException("Incorrect JWT token");
        }

        UserDto userDto = userDtoResponseEntity.getBody();

        if (!userDto.getUsername().equals("admin") && !userDto.getRole().equals(Role.ADMIN)){
            log.warn("You don't have enough rights on service {} method: getAllWithdrawalsByWithdrawalStatus", AdminServiceImpl.class);
            throw new InvalidCredentialsException("You don't have enough rights");
        }

        log.info("Finding all withdrawals by status order by timestamp desc on service {} " +
                "method: getAllWithdrawalsByWithdrawalStatus", AdminServiceImpl.class);

        return withdrawalRepository.findAllByStatusOrderByTimestampDesc(withdrawalStatus);
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public void updateWithdrawalStatusById(String jwtToken, WithdrawalStatus withdrawalStatus, long id) {
        log.info("Starting method updateWithdrawalByWithdrawalStatus on service {} method: updateWithdrawalByWithdrawalStatus", AdminServiceImpl.class);

        log.info("Receiving entity user by JWT token on service {} method: updateWithdrawalByWithdrawalStatus", AdminServiceImpl.class);

        ResponseEntity<UserDto> userDtoResponseEntity =
                restTemplate.postForEntity(JWT_URL + "?jwtToken=" + jwtToken, null, UserDto.class);

        if (userDtoResponseEntity.getStatusCode().isError()){
            log.warn("Incorrect JWT token on service {} method: updateWithdrawalByWithdrawalStatus", AdminServiceImpl.class);
            throw new InvalidCredentialsException("Incorrect JWT token");
        }

        UserDto userDto = userDtoResponseEntity.getBody();

        if (!userDto.getUsername().equals("admin") && !userDto.getRole().equals(Role.ADMIN)){
            log.warn("You don't have enough rights on service {} method: updateWithdrawalByWithdrawalStatus", AdminServiceImpl.class);
            throw new InvalidCredentialsException("You don't have enough rights");
        }

        withdrawalRepository.updateStatusById(withdrawalStatus, id);
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public void handleBlockUserById(String jwtToken, long id) {
        log.info("Starting method handleBlockUserById on service {} method: handleBlockUserById", AdminServiceImpl.class);

        log.info("Receiving entity user by JWT token on service {} method: handleBlockUserById", AdminServiceImpl.class);

        ResponseEntity<UserDto> userDtoResponseEntity =
                restTemplate.postForEntity(JWT_URL + "?jwtToken=" + jwtToken, null, UserDto.class);

        if (userDtoResponseEntity.getStatusCode().isError()){
            log.warn("Incorrect JWT token on service {} method: handleBlockUserById", AdminServiceImpl.class);
            throw new InvalidCredentialsException("Incorrect JWT token");
        }

        UserDto userDto = userDtoResponseEntity.getBody();

        if (!userDto.getUsername().equals("admin") && !userDto.getRole().equals(Role.ADMIN)){
            log.warn("You don't have enough rights on service {} method: handleBlockUserById", AdminServiceImpl.class);
            throw new InvalidCredentialsException("You don't have enough rights");
        }

        log.info("Update user isAccountNonLocked by Id on service {} method: handleBlockUserById", AdminServiceImpl.class);

        userRepository.updateIsAccountNonLocked(false, id);
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public void handleUnblockUserById(String jwtToken, long id) {
        log.info("Starting method handleUnblockUserById on service {} method: handleUnblockUserById", AdminServiceImpl.class);

        log.info("Receiving entity user by JWT token on service {} method: handleUnblockUserById", AdminServiceImpl.class);

        ResponseEntity<UserDto> userDtoResponseEntity =
                restTemplate.postForEntity(JWT_URL + "?jwtToken=" + jwtToken, null, UserDto.class);

        if (userDtoResponseEntity.getStatusCode().isError()){
            log.warn("Incorrect JWT token on service {} method: handleUnblockUserById", AdminServiceImpl.class);
            throw new InvalidCredentialsException("Incorrect JWT token");
        }

        UserDto userDto = userDtoResponseEntity.getBody();

        if (!userDto.getUsername().equals("admin") && !userDto.getRole().equals(Role.ADMIN)){
            log.warn("You don't have enough rights on service {} method: handleUnblockUserById", AdminServiceImpl.class);
            throw new InvalidCredentialsException("You don't have enough rights");
        }

        log.info("Update user isAccountNonLocked by Id on service {} method: handleUnblockUserById", AdminServiceImpl.class);

        userRepository.updateIsAccountNonLocked(true, id);
    }
}
