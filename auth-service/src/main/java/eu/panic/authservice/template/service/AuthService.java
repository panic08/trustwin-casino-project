package eu.panic.authservice.template.service;

import eu.panic.authservice.template.payload.*;
import eu.panic.authservice.template.entity.User;
import eu.panic.authservice.template.payload.google.GoogleSignInRequest;
import jakarta.transaction.Transactional;

public interface AuthService {
    SignInResponse handleSignUp(SignUpRequest signUpRequest);
    SignInResponse handleSignIn(SignInRequest signInRequest);
    SignInResponse handleSignInByGoogle(GoogleSignInRequest googleSignInRequest);
    User getInfoByJwt(String jwtToken);
    ChangePersonalDataResponse handleChangePersonalData(String jwtToken, ChangePersonalDataRequest changePersonalDataRequest);
    ChangeServerSeedResponse handleChangeServerSeed(String jwtToken);

    ChangeClientSeedResponse handleChangeClientSeed(String jwtToken, String clientSeed);
}
