package eu.panic.authservice.template.service;

import eu.panic.authservice.template.payload.*;
import eu.panic.authservice.template.entity.User;
import eu.panic.authservice.template.payload.google.GoogleSignInRequest;

public interface AuthService {
    SignInResponse handleSignUp(SignUpRequest signUpRequest);
    SignInResponse handleSignIn(SignInRequest signInRequest);
    SignInResponse handleSignInByGoogle(GoogleSignInRequest googleSignInRequest);
    User getInfoByJwt(String jwtToken);
    void changePersonalData(String jwtToken, ChangePersonalDataRequest changePersonalDataRequest);
}
