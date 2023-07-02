package eu.panic.authservice.template.controller;

import eu.panic.authservice.template.dto.ChangePersonalDataRequest;
import eu.panic.authservice.template.dto.SignInRequest;
import eu.panic.authservice.template.dto.SignUpRequest;
import eu.panic.authservice.template.dto.SignUpResponse;
import eu.panic.authservice.template.entity.User;
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
    private SignUpResponse signUp(@RequestBody SignUpRequest signUpRequest){
        return authService.handleSignUp(signUpRequest);
    }

    @PostMapping("/signIn")
    private SignUpResponse signIn(@RequestBody SignInRequest signInRequest){
        return authService.handleSignIn(signInRequest);
    }

    @PostMapping("/getInfoByJwt")
    private User getInfoByJwt(@RequestParam String jwtToken){
        return authService.getInfoByJwt(jwtToken);
    }

    @PutMapping("/changePersonalData")
    private void changePersonalData(
            @RequestHeader String jwtToken,
            @RequestBody ChangePersonalDataRequest changePersonalDataRequest
    ){
        authService.changePersonalData(jwtToken, changePersonalDataRequest);
    }
}
