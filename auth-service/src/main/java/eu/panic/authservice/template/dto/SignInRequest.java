package eu.panic.authservice.template.dto;

import eu.panic.authservice.template.entity.SignInHistory;

public record SignInRequest(String username, String password, SignInHistory.Data data) {
}
