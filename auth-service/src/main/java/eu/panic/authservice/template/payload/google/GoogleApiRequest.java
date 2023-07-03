package eu.panic.authservice.template.payload.google;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoogleApiRequest {
    private String client_id;
    private String client_secret;
    private String redirect_uri;
    private String code;
    private String grant_type;
}
