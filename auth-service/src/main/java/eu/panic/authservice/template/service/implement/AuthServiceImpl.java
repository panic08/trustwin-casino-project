package eu.panic.authservice.template.service.implement;

import eu.panic.authservice.security.jwt.JwtUtil;
import eu.panic.authservice.template.dto.ChangePersonalDataRequest;
import eu.panic.authservice.template.dto.SignInRequest;
import eu.panic.authservice.template.dto.SignUpRequest;
import eu.panic.authservice.template.dto.SignUpResponse;
import eu.panic.authservice.template.entity.SignInHistory;
import eu.panic.authservice.template.entity.User;
import eu.panic.authservice.template.enums.Role;
import eu.panic.authservice.template.exception.InvalidCredentialsException;
import eu.panic.authservice.template.repository.SignInHistoryRepository;
import eu.panic.authservice.template.repository.UserRepository;
import eu.panic.authservice.template.service.AuthService;
import eu.panic.authservice.util.SeedGeneratorUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    public AuthServiceImpl(UserRepository userRepository, SignInHistoryRepository signInHistoryRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.signInHistoryRepository = signInHistoryRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    private final UserRepository userRepository;
    private final SignInHistoryRepository signInHistoryRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    @Override
    @Transactional(rollbackOn = Throwable.class)
    public SignUpResponse handleSignUp(SignUpRequest signUpRequest) {
        log.info("Starting method handleSignUp on service {}, method: handleSignUp", AuthServiceImpl.class);

        if (signUpRequest.username().length() < 5 || signUpRequest.username().length() > 12){
            log.warn("Username cannot be less than 5 characters or more than 12 characters on class {}, method: handleSignUp", AuthServiceImpl.class);
            throw new InvalidCredentialsException("Username cannot be less than 5 characters or more than 12 characters");
        }

        if (signUpRequest.password().length() < 5){
            log.warn("Password cannot be less than 5 characters on class {}, method: handleSignUp", AuthServiceImpl.class);
            throw new InvalidCredentialsException("Password cannot be less than 5 characters");
        }

        if (userRepository.findUserByUsername(signUpRequest.username()) != null
                ||
                userRepository.findUserByEmail(signUpRequest.email()) != null){
            log.warn("An account with this username or email already exists on class {}, method: handleSignUp", AuthServiceImpl.class);
            throw new InvalidCredentialsException("An account with this username or email already exists. Think of another one.");
        }

        log.info("Creating a new entity user on service {}, method: handleSignUp", AuthServiceImpl.class);

        User user = new User();

        user.setUsername(signUpRequest.username());
        user.setEmail(signUpRequest.email());
        user.setPassword(passwordEncoder.encode(signUpRequest.password()));
        user.setRole(Role.DEFAULT);
        user.setBalance(0L);
        user.setIsMultiAccount(userRepository.existsByIpAddress(signUpRequest.data().getIpAddress()));
        user.setIsAccountNonLocked(true);
        user.setIpAddress(signUpRequest.data().getIpAddress());
        user.setPersonalData(new User.PersonalData(null, null, null, null));
        user.setData(new User.Data(SeedGeneratorUtil.generateSeed(), SeedGeneratorUtil.generateSeed()));
        user.setRegisteredAt(System.currentTimeMillis());

        log.info("Saving a new entity user on service {}, method: handleSignUp", AuthServiceImpl.class);

        userRepository.save(user);

        log.info("Creating a new entity signInHistory on service {}, method: handleSignUp", AuthServiceImpl.class);

        SignInHistory signInHistory = new SignInHistory();

        signInHistory.setUsername(signUpRequest.username());
        signInHistory.setTimestamp(System.currentTimeMillis());
        signInHistory.setData(signUpRequest.data());

        log.info("Saving a new entity signInHistory on service {}, method: handleSignUp", AuthServiceImpl.class);

        signInHistoryRepository.save(signInHistory);

        log.info("Creating a response for this method on service {}, method: handleSignUp", AuthServiceImpl.class);

        SignUpResponse signUpResponse = new SignUpResponse();

        signUpResponse.setJwtToken(jwtUtil.generateToken(user));
        signUpResponse.setTimestamp(System.currentTimeMillis());

        return signUpResponse;
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public SignUpResponse handleSignIn(SignInRequest signInRequest) {
        log.info("Starting method handleSignIn on service {}, method: handleSignIn", AuthServiceImpl.class);

        log.info("Finding User by Username on service {}, method: handleSignIn", AuthServiceImpl.class);
        User user = userRepository.findUserByUsername(signInRequest.username());

        if (user == null || !passwordEncoder.matches(signInRequest.password(), user.getPassword())) {
            log.warn("Incorrect login or password on class {}, method: handleSignUp", AuthServiceImpl.class);
            throw new InvalidCredentialsException("Nice try. Incorrect login or password");
        }

        log.info("Creating a new entity signInHistory on service {}, method: handleSignUp", AuthServiceImpl.class);

        SignInHistory signInHistory = new SignInHistory();

        signInHistory.setUsername(signInRequest.username());
        signInHistory.setData(signInRequest.data());
        signInHistory.setTimestamp(System.currentTimeMillis());

        log.info("Saving a new entity signInHistory on service {}, method: handleSignUp", AuthServiceImpl.class);

        signInHistoryRepository.save(signInHistory);

        log.info("Creating a response for this method on service {}, method: handleSignUp", AuthServiceImpl.class);

        SignUpResponse signUpResponse = new SignUpResponse();

        signUpResponse.setJwtToken(jwtUtil.generateToken(user));
        signUpResponse.setTimestamp(System.currentTimeMillis());

        return signUpResponse;
    }

    @Override
    public User getInfoByJwt(String jwtToken) {
        log.info("Starting method getInfoByJwt on service {}, method: getInfoByJwt", AuthServiceImpl.class);

        if (!jwtUtil.isJwtValid(jwtToken) || jwtUtil.isTokenExpired(jwtToken)){
            log.warn("Incorrect JWT token on class {}, method: handleSignUp", AuthServiceImpl.class);
            throw new InvalidCredentialsException("Incorrect JWT token");
        }

        return userRepository.findUserByUsername(jwtUtil.extractUsername(jwtToken));
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public void changePersonalData(String jwtToken, ChangePersonalDataRequest changePersonalDataRequest) {
        log.info("Starting method changePersonalData on service {}, method: changePersonalData", AuthServiceImpl.class);

        if (!jwtUtil.isJwtValid(jwtToken) || jwtUtil.isTokenExpired(jwtToken)){
            log.warn("Incorrect JWT token on class {}, method: handleSignUp", AuthServiceImpl.class);
            throw new InvalidCredentialsException("Incorrect JWT token");
        }

        log.info("Creating a new record userPersonalData on service {}, method: changePersonalData", AuthServiceImpl.class);

        User.PersonalData userPersonalData = new User.PersonalData(
                changePersonalDataRequest.firstname(),
                changePersonalDataRequest.lastname(),
                changePersonalDataRequest.birthday(),
                changePersonalDataRequest.gender()
        );

        log.info("Update PersonalData by Username on service {}, method: changePersonalData", AuthServiceImpl.class);

        userRepository.updatePersonalDataByUsername(userPersonalData, jwtUtil.extractUsername(jwtToken));
    }
}
