package eu.panic.authservice.template.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInResponse {
    private String jwtToken;
    private Long timestamp;
}
