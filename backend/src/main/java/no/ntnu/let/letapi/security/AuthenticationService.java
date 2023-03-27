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

/**
 * Service for authentication
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    public static final TemporalAmount TOKEN_DURATION = ChronoUnit.MINUTES.getDuration().multipliedBy(10); // 10 minutes
    private final JwtEncoder encoder;
    private final JwtDecoder decoder;
    private final UserService userService;

    /**
     * Get the current user
     * @param authentication The authentication object
     * @return The current user
     */
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

    /**
     * Renew a token
     * @param token The token to renew
     * @return The renewed token
     */
    public String renewToken(String token) {
        User user = this.userService.getUserByEmail(this.decoder.decode(token).getSubject());
        UserDetails userDetails = new UserDetailsImpl(user);
        Authentication authentication = new UserAuthentication(userDetails);
        return this.generateToken(authentication);
    }

    /**
     * Get the expiration date of a token
     * @param token The token
     * @return The expiration date
     */
    public Instant getExpirationDate(String token) {
        return this.decoder.decode(token).getExpiresAt();
    }

    public Authentication getAuthentication(String token) {
        String email = this.decoder.decode(token).getSubject();
        User user = this.userService.getUserByEmail(email);
        UserDetails userDetails = new UserDetailsImpl(user);

        return new UserAuthentication(userDetails);
    }

    /**
     * Get the currently logged-in user
     * @return The currently logged-in user
     */
    public User getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return null;
        String email = auth.getName();
        return this.userService.getUserByEmail(email);
    }

    /**
     * Check if the currently logged-in user is an admin or allowed
     * @param allowedTest The test to check if the user is allowed
     * @return True if the user is an admin or allowed
     */
    public Boolean isAdminOrAllowed(Predicate<User> allowedTest) {
        User user = this.getLoggedInUser();
        if (user == null) return false;
        return user.getAdmin() || allowedTest.test(user);
    }

    /**
     * Check if the currently logged-in user is allowed
     * @param allowedTest The test to check if the user is allowed
     * @return True if the user is allowed
     */
    public Boolean isAllowed(Predicate<User> allowedTest) {
        User user = this.getLoggedInUser();
        if (user == null) return null;
        return allowedTest.test(user);
    }

    /**
     * Check if the currently logged-in user is an admin
     * @return True if the user is an admin
     */
    public Boolean isAdmin() {
        User user = this.getLoggedInUser();
        if (user == null) return null;
        return user.getAdmin();
    }


    /**
     * Get a user from a token
     * @param token The token
     * @return The user
     */
    public User getUser(String token) {
        String email = this.decoder.decode(token).getSubject();
        return this.userService.getUserByEmail(email);
    }

}
