package eu.panic.authservice.template.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GoogleCertsDto {
    @JsonProperty("keys")
    private List<KeyDto> keys;
    @Getter
    @Setter
    public static class KeyDto {
        @JsonProperty("alg")
        private String alg;

        @JsonProperty("kty")
        private String kty;

        @JsonProperty("e")
        private String e;

        @JsonProperty("kid")
        private String kid;

        @JsonProperty("n")
        private String n;

        @JsonProperty("use")
        private String use;
    }
}
