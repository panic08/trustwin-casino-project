package eu.panic.authservice.template.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpResponse {
    private String jwtToken;
    private Long timestamp;
}
