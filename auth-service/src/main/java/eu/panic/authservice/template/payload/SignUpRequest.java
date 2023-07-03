package eu.panic.authservice.template.payload;

import eu.panic.authservice.template.entity.SignInHistory;

public record SignUpRequest(String username, String email, String password, SignInHistory.Data data) {
}
