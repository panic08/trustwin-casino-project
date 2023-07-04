package eu.panic.authservice.security.jwt;

import eu.panic.authservice.template.payload.google.GoogleSignInResponse;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    @Value("${spring.security.jwt.secret}")
    private String secret;
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(secret.getBytes()).build().parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean isJwtValid(String jwt) {
        try {
            Jwts.parserBuilder().setSigningKey(secret.getBytes()).build().parseClaimsJws(jwt);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + 1000 * 60 * 60 * 24 * 15);
        byte[] signingKeyBytes = secret.getBytes();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(Keys.hmacShaKeyFor(signingKeyBytes), SignatureAlgorithm.HS256)
                .compact();
    }

    public GoogleSignInResponse decodeJwt(String jwt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        /*
            TODO Move the business logic in this method to another class
            */
        String kid = "9341dedeee2d1869b657fa930300082fe26b3d92";
        String modulus = "tid8bJCI5FxtvMiVHq8pRZBGIPaG9mEa1akpIC9munzxA3mWoc-KoR1TGkocu76WFthaZnPS31WJbRVChU6g4EMIg7E8Ltpxifk1PQu0qqbLcpnoI62ojsB7l_Z_lkls0NUzTuKGMMtNoJsDrL1BT0UzcnWerh2PwzDAMpfPgafWdT2IYGTx1gNLcNOWpPhDgMSQqUmIPwCmxdan4i4OMd7lJYQ1WQlN8VnQgbRgHrm1zImY6MPqho9jW3Ub5FwGbunwCDrP9a2dD_5Iwm7_lR82iB4BGlu28WxFn0fm5DgZAeAFSGKE1xblC97WrjnPh2XYTx6pxsea_Hn71VcNSQ";
        String exponent = "AQAB";


        BigInteger modulusBigInt = new BigInteger(1, Base64.getUrlDecoder().decode(modulus));
        BigInteger exponentBigInt = new BigInteger(1, Base64.getUrlDecoder().decode(exponent));

        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(modulusBigInt, exponentBigInt);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);


        Jwt<?, ?> jwtd = Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parse(jwt);

        Claims claims = (Claims) jwtd.getBody();


        GoogleSignInResponse googleSignInResponse =  new GoogleSignInResponse();

        googleSignInResponse.setName(claims.get("name", String.class));
        googleSignInResponse.setPicture(claims.get("picture", String.class));
        googleSignInResponse.setEmail(claims.get("email", String.class));
        googleSignInResponse.setLocale(claims.get("locale", String.class));
        googleSignInResponse.setGiven_name(claims.get("given_name", String.class));
        return googleSignInResponse;
    }


}