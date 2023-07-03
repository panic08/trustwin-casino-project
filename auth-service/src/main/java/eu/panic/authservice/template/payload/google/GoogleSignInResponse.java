package eu.panic.authservice.template.payload.google;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoogleSignInResponse {
    private String email;
    private String name;
    private String picture;
    private String given_name;
    private String locale;
}
