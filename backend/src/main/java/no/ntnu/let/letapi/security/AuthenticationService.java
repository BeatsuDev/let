package no.ntnu.let.letapi.security;

import lombok.RequiredArgsConstructor;
import no.ntnu.let.letapi.model.user.User;
import no.ntnu.let.letapi.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtEncoder encoder;
    private final JwtDecoder decoder;
    private final UserService userService;

    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(5, ChronoUnit.MINUTES))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String renewToken(String token) {
        Instant now = Instant.now();
        Jwt currentClaims = decoder.decode(token);
        JwtClaimsSet newClaims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(5, ChronoUnit.MINUTES))
                .subject(currentClaims.getSubject())
                .claim("scope", currentClaims.getClaim("scope"))
                .build();
        return encoder.encode(JwtEncoderParameters.from(newClaims)).getTokenValue();
    }

    public Instant getExpirationDate(String token) {
        return this.decoder.decode(token).getExpiresAt();
    }

    public User getUser(String token) {
        String email = this.decoder.decode(token).getSubject();
        return this.userService.getUserByEmail(email);
    }

}
