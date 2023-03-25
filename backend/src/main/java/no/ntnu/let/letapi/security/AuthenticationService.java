package no.ntnu.let.letapi.security;

import lombok.RequiredArgsConstructor;
import no.ntnu.let.letapi.model.user.User;
import no.ntnu.let.letapi.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    public static final TemporalAmount TOKEN_DURATION = ChronoUnit.MINUTES.getDuration().multipliedBy(5); // 5 minutes
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
                .expiresAt(now.plus(TOKEN_DURATION))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String renewToken(String token) {
        User user = this.userService.getUserByEmail(this.decoder.decode(token).getSubject());
        UserDetails userDetails = new UserDetailsImpl(user);
        Authentication authentication = new UserAuthentication(userDetails);
        return this.generateToken(authentication);
    }

    public Instant getExpirationDate(String token) {
        return this.decoder.decode(token).getExpiresAt();
    }

    public Authentication getAuthentication(String token) {
        String email = this.decoder.decode(token).getSubject();
        User user = this.userService.getUserByEmail(email);
        UserDetails userDetails = new UserDetailsImpl(user);

        return new UserAuthentication(userDetails);
    }

    public User getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return null;
        String email = auth.getName();
        return this.userService.getUserByEmail(email);
    }

    public Boolean isAdminOrAllowed(Predicate<User> allowedTest) {
        User user = this.getLoggedInUser();
        if (user == null) return false;
        return user.getAdmin() || allowedTest.test(user);
    }

    public Boolean isAllowed(Predicate<User> allowedTest) {
        User user = this.getLoggedInUser();
        if (user == null) return null;
        return allowedTest.test(user);
    }

    public Boolean isAdmin() {
        User user = this.getLoggedInUser();
        if (user == null) return null;
        return user.getAdmin();
    }


    public User getUser(String token) {
        String email = this.decoder.decode(token).getSubject();
        return this.userService.getUserByEmail(email);
    }

}
