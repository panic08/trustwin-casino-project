package eu.panic.authservice.template.payload.google;

public record GoogleApiResponse(
        String access_token,
        String expires_in,
        String scope,
        String token_type,
        String id_token
) {
}
