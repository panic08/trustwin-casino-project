package eu.panic.authservice.template.dto;

import eu.panic.authservice.template.entity.SignInHistory;

public record SignUpRequest(String username, String email, String password, SignInHistory.Data data) {
}
