package eu.panic.authservice.template.service;

import eu.panic.authservice.template.dto.ChangePersonalDataRequest;
import eu.panic.authservice.template.dto.SignInRequest;
import eu.panic.authservice.template.dto.SignUpRequest;
import eu.panic.authservice.template.dto.SignUpResponse;
import eu.panic.authservice.template.entity.User;

public interface AuthService {
    SignUpResponse handleSignUp(SignUpRequest signUpRequest);
    SignUpResponse handleSignIn(SignInRequest signInRequest);
    User getInfoByJwt(String jwtToken);
    void changePersonalData(String jwtToken, ChangePersonalDataRequest changePersonalDataRequest);
}
