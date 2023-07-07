package eu.panic.authservice.template.payload.google;

import eu.panic.authservice.template.entity.SignInHistory;

public record GoogleSignInRequest(String code, SignInHistory.Data data, String referral) {
}
