package eu.panic.authservice.template.controller;

import eu.panic.authservice.template.payload.*;
import eu.panic.authservice.template.entity.User;
import eu.panic.authservice.template.payload.google.GoogleSignInRequest;
import eu.panic.authservice.template.service.implement.AuthServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    public AuthController(AuthServiceImpl authService) {
        this.authService = authService;
    }
    private final AuthServiceImpl authService;

    @PostMapping("/signUp")
    private SignInResponse signUp(@RequestBody SignUpRequest signUpRequest){
        return authService.handleSignUp(signUpRequest);
    }

    @PostMapping("/signIn")
    private SignInResponse signIn(@RequestBody SignInRequest signInRequest){
        return authService.handleSignIn(signInRequest);
    }
    @PostMapping("/signInByGoogle")
    private SignInResponse signInByGoogle(@RequestBody GoogleSignInRequest googleSignInRequest){
        return authService.handleSignInByGoogle(googleSignInRequest);
    }

    @PostMapping("/getInfoByJwt")
    private User getInfoByJwt(@RequestParam String jwtToken){
        return authService.getInfoByJwt(jwtToken);
    }

    @PutMapping("/changePersonalData")
    private ChangePersonalDataResponse changePersonalData(
            @RequestHeader String jwtToken,
            @RequestBody ChangePersonalDataRequest changePersonalDataRequest
    ){
        return authService.handleChangePersonalData(jwtToken, changePersonalDataRequest);
    }

    @PutMapping("/changeServerSeed")
    private ChangeServerSeedResponse changeServerSeed(@RequestHeader String jwtToken){
        return authService.handleChangeServerSeed(jwtToken);
    }

    @PutMapping("/changeClientSeed")
    private ChangeClientSeedResponse changeClientSeed(
            @RequestHeader String jwtToken,
            @RequestBody ChangeClientSeedRequest changeClientSeedRequest
    ){
        return authService.handleChangeClientSeed(jwtToken, changeClientSeedRequest.clientSeed());
    }
}
