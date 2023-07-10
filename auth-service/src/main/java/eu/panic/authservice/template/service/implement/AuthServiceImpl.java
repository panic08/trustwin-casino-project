package eu.panic.authservice.template.service.implement;

import eu.panic.authservice.security.jwt.JwtUtil;
import eu.panic.authservice.template.enums.AuthorizeType;
import eu.panic.authservice.template.enums.Rank;
import eu.panic.authservice.template.payload.*;
import eu.panic.authservice.template.entity.SignInHistory;
import eu.panic.authservice.template.entity.User;
import eu.panic.authservice.template.enums.Role;
import eu.panic.authservice.template.exception.InvalidCredentialsException;
import eu.panic.authservice.template.payload.google.GoogleApiRequest;
import eu.panic.authservice.template.payload.google.GoogleApiResponse;
import eu.panic.authservice.template.payload.google.GoogleSignInRequest;
import eu.panic.authservice.template.payload.google.GoogleSignInResponse;
import eu.panic.authservice.template.repository.SignInHistoryRepository;
import eu.panic.authservice.template.repository.UserRepository;
import eu.panic.authservice.template.service.AuthService;
import eu.panic.authservice.util.RandomCharacterGenerator;
import eu.panic.authservice.util.SeedGeneratorUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    public AuthServiceImpl(UserRepository userRepository, SignInHistoryRepository signInHistoryRepository, PasswordEncoder passwordEncoder, RestTemplate restTemplate, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.signInHistoryRepository = signInHistoryRepository;
        this.passwordEncoder = passwordEncoder;
        this.restTemplate = restTemplate;
        this.jwtUtil = jwtUtil;
    }

    private final UserRepository userRepository;
    private final SignInHistoryRepository signInHistoryRepository;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;
    private static final String google_client_id = "613917991989-ccheeqfpn4dq1lrcmam52ddlfcvd2fmi.apps.googleusercontent.com";
    private static final String google_client_secret = "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";
    private static final String google_redirect_uri = "http://localhost:3000/authorize";
    private static final String GOOGLE_API_OAUTH2_URL = "https://www.googleapis.com/oauth2/v4/token";
    @Override
    @Transactional(rollbackOn = Throwable.class)
    public SignInResponse handleSignUp(SignUpRequest signUpRequest) {
        log.info("Starting method handleSignUp on service {}, method: handleSignUp", AuthServiceImpl.class);

        if (signUpRequest.username().length() < 5 || signUpRequest.username().length() > 12){
            log.warn("Username cannot be less than 5 characters or more than 12 characters on service {}, method: handleSignUp", AuthServiceImpl.class);
            throw new InvalidCredentialsException("Username cannot be less than 5 characters or more than 12 characters");
        }

        if (signUpRequest.password().length() < 5){
            log.warn("Password cannot be less than 5 characters on service {}, method: handleSignUp", AuthServiceImpl.class);
            throw new InvalidCredentialsException("Password cannot be less than 5 characters");
        }

        if (userRepository.findUserByUsername(signUpRequest.username()) != null
                ||
                userRepository.findUserByEmail(signUpRequest.email()) != null){
            log.warn("An account with this username or email already exists on service {}, method: handleSignUp", AuthServiceImpl.class);
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
        user.setPersonalData(new User.PersonalData(signUpRequest.username(), null, null));
        user.setData(new User.Data(AuthorizeType.DEFAULT, SeedGeneratorUtil.generateSeed(), SeedGeneratorUtil.generateSeed(), Rank.NEWBIE));
        user.setRefData(new User.RefData(0L, 0L, 1, RandomCharacterGenerator.generateRandomCharacters(10), signUpRequest.referral()));
        user.setRegisteredAt(System.currentTimeMillis());

        log.info("Saving a new entity user on service {}, method: handleSignUp", AuthServiceImpl.class);

        userRepository.save(user);

        log.info("Updating referral entity user on service {}, method: handleSignUp", AuthServiceImpl.class);

        if (user.getRefData().getInvitedBy() != null) {
            User user1 = userRepository.findUserByRefData_RefLink(user.getRefData().getInvitedBy());

            User.RefData refData1 = user1.getRefData();
            refData1.setInvited(refData1.getInvited() + 1);

            if (refData1.getInvited() >= 500L) {
                refData1.setLevel(5);
            } else if (refData1.getInvited() >= 250L) {
                refData1.setLevel(4);
            } else if (refData1.getInvited() >= 100L) {
                refData1.setLevel(3);
            } else if (refData1.getInvited() >= 10L) {
                refData1.setLevel(2);
            } else if (refData1.getInvited() >= 0) {
                refData1.setLevel(1);
            }

            user1.setRefData(refData1);

            log.info("Saving a referral entity user on service {}, method: handleSignInByGoogle", AuthServiceImpl.class);

            userRepository.save(user1);
        }
        log.info("Creating a new entity signInHistory on service {}, method: handleSignUp", AuthServiceImpl.class);

        SignInHistory signInHistory = new SignInHistory();

        signInHistory.setUsername(signUpRequest.username());
        signInHistory.setTimestamp(System.currentTimeMillis());
        signInHistory.setData(signUpRequest.data());

        log.info("Saving a new entity signInHistory on service {}, method: handleSignUp", AuthServiceImpl.class);

        signInHistoryRepository.save(signInHistory);



        log.info("Creating a response for this method on service {}, method: handleSignUp", AuthServiceImpl.class);

        SignInResponse signInResponse = new SignInResponse();

        signInResponse.setJwtToken(jwtUtil.generateToken(user));
        signInResponse.setTimestamp(System.currentTimeMillis());

        return signInResponse;
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public SignInResponse handleSignIn(SignInRequest signInRequest) {
        log.info("Starting method handleSignIn on service {}, method: handleSignIn", AuthServiceImpl.class);

        log.info("Finding User by Username on service {}, method: handleSignIn", AuthServiceImpl.class);
        User user = userRepository.findUserByUsername(signInRequest.username());

        if (user == null || !passwordEncoder.matches(signInRequest.password(), user.getPassword())) {
            log.warn("Incorrect login or password on service {}, method: handleSignUp", AuthServiceImpl.class);
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

        SignInResponse signInResponse = new SignInResponse();

        signInResponse.setJwtToken(jwtUtil.generateToken(user));
        signInResponse.setTimestamp(System.currentTimeMillis());

        return signInResponse;
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public SignInResponse handleSignInByGoogle(GoogleSignInRequest googleSignInRequest) {
        log.info("Starting method handleSignInByGoogle on service {}, method: handleSignInByGoogle", AuthServiceImpl.class);

        log.info("Creating googleApiRequest request on service {}, method: handleSignInByGoogle", AuthServiceImpl.class);

        GoogleApiRequest googleApiRequest = new GoogleApiRequest();

        googleApiRequest.setClient_id(google_client_id);
        googleApiRequest.setClient_secret(google_client_secret);
        googleApiRequest.setRedirect_uri(google_redirect_uri);
        googleApiRequest.setCode(URLDecoder.decode(googleSignInRequest.code(), StandardCharsets.UTF_8));
        googleApiRequest.setGrant_type("authorization_code");

        log.info("Receiving JWT token from Google Api Service on service {}, method: handleSignInByGoogle", AuthServiceImpl.class);

        ResponseEntity<GoogleApiResponse> googleApiResponseResponseEntity =
                restTemplate.postForEntity(GOOGLE_API_OAUTH2_URL, googleApiRequest, GoogleApiResponse.class);

        log.info("Decoding Google JWT on service {}, method: handleSignInByGoogle", AuthServiceImpl.class);

        GoogleSignInResponse googleSignInResponse  = null;
        try {
            googleSignInResponse = jwtUtil.decodeJwt(googleApiResponseResponseEntity.getBody().id_token());
        } catch (GeneralSecurityException e){
            e.printStackTrace();
        }

        log.info("Finding User by Email on service {}, method: handleSignInByGoogle", AuthServiceImpl.class);

        User user = userRepository.findUserByEmail(googleSignInResponse.getEmail());

        log.info("Creating a response for this method on service {}, method: handleSignInByGoogle", AuthServiceImpl.class);

        SignInResponse signInResponse = new SignInResponse();

        log.info("Check account for \"existence\" on service {}, method: handleSignInByGoogle", AuthServiceImpl.class);

        if (user != null) {
            signInResponse.setJwtToken(jwtUtil.generateToken(user));
            signInResponse.setTimestamp(System.currentTimeMillis());
        }else{
            log.info("Creating new entity user on service {}, method: handleSignInByGoogle", AuthServiceImpl.class);
            user = new User();
            user.setUsername(RandomCharacterGenerator.generateRandomCharacters(70));
            user.setEmail(googleSignInResponse.getEmail());
            user.setRole(Role.DEFAULT);
            user.setPassword(RandomCharacterGenerator.generateRandomCharacters(70));
            user.setBalance(0L);
            user.setIpAddress(googleSignInRequest.data().getIpAddress());
            user.setData(new User.Data(AuthorizeType.GOOGLE, SeedGeneratorUtil.generateSeed(), SeedGeneratorUtil.generateSeed(), Rank.NEWBIE));
            user.setPersonalData(new User.PersonalData(googleSignInResponse.getName(), null, null));
            user.setRefData(new User.RefData(0L, 0L, 1, RandomCharacterGenerator.generateRandomCharacters(10), googleSignInRequest.referral()));
            user.setIsAccountNonLocked(true);
            user.setIsMultiAccount(userRepository.existsByIpAddress(googleSignInRequest.data().getIpAddress()));
            user.setRegisteredAt(System.currentTimeMillis());

            log.info("Saving a new entity user on service {}, method: handleSignInByGoogle", AuthServiceImpl.class);

            userRepository.save(user);

            log.info("Updating referral entity user on service {}, method: handleSignInByGoogle", AuthServiceImpl.class);

            if (user.getRefData().getInvitedBy() != null) {
                User user1 = userRepository.findUserByRefData_RefLink(user.getRefData().getInvitedBy());

                User.RefData refData1 = user1.getRefData();
                refData1.setInvited(refData1.getInvited() + 1);

                if (refData1.getInvited() >= 500L) {
                    refData1.setLevel(5);
                } else if (refData1.getInvited() >= 250L) {
                    refData1.setLevel(4);
                } else if (refData1.getInvited() >= 100L) {
                    refData1.setLevel(3);
                } else if (refData1.getInvited() >= 10L) {
                    refData1.setLevel(2);
                } else if (refData1.getInvited() >= 0) {
                    refData1.setLevel(1);
                }

                user1.setRefData(refData1);

                log.info("Saving a referral entity user on service {}, method: handleSignInByGoogle", AuthServiceImpl.class);

                userRepository.save(user1);
            }
            signInResponse.setJwtToken(jwtUtil.generateToken(user));
            signInResponse.setTimestamp(System.currentTimeMillis());
        }
        log.info("Creating new entity signInHistory on service {}, method: handleSignInByGoogle", AuthServiceImpl.class);

        SignInHistory signInHistory = new SignInHistory();

        signInHistory.setUsername(user.getUsername());
        signInHistory.setData(googleSignInRequest.data());
        signInHistory.setTimestamp(System.currentTimeMillis());

        log.info("Saving a new entity user on service {}, method: handleSignInByGoogle", AuthServiceImpl.class);

        signInHistoryRepository.save(signInHistory);


        return signInResponse;
    }

    @Override
    public User getInfoByJwt(String jwtToken) {
        log.info("Starting method getInfoByJwt on service {}, method: getInfoByJwt", AuthServiceImpl.class);

        if (!jwtUtil.isJwtValid(jwtToken) || jwtUtil.isTokenExpired(jwtToken)){
            log.warn("Incorrect JWT token on service {}, method: handleSignUp", AuthServiceImpl.class);
            throw new InvalidCredentialsException("Incorrect JWT token");
        }

        return userRepository.findUserByUsername(jwtUtil.extractUsername(jwtToken));
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public void changePersonalData(String jwtToken, ChangePersonalDataRequest changePersonalDataRequest) {
        log.info("Starting method changePersonalData on service {}, method: changePersonalData", AuthServiceImpl.class);

        if (!jwtUtil.isJwtValid(jwtToken) || jwtUtil.isTokenExpired(jwtToken)){
            log.warn("Incorrect JWT token on service {}, method: handleSignUp", AuthServiceImpl.class);
            throw new InvalidCredentialsException("Incorrect JWT token");
        }

        log.info("Creating a new record userPersonalData on service {}, method: changePersonalData", AuthServiceImpl.class);

        User.PersonalData userPersonalData = new User.PersonalData(
                changePersonalDataRequest.nickname(),
                changePersonalDataRequest.birthday(),
                changePersonalDataRequest.gender()
        );

        log.info("Update PersonalData by Username on service {}, method: changePersonalData", AuthServiceImpl.class);

        userRepository.updatePersonalDataByUsername(userPersonalData, jwtUtil.extractUsername(jwtToken));
    }
}
