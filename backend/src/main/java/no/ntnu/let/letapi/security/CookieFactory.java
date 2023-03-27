package no.ntnu.let.letapi.security;

import jakarta.servlet.http.Cookie;

import java.time.temporal.ChronoUnit;

public class CookieFactory {
    public static Cookie getAuthorizationCookie(String token) {
        Cookie cookie = new Cookie("Authorization", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge((int) AuthenticationService.TOKEN_DURATION.get(ChronoUnit.SECONDS));
        return cookie;
    }

    public static Cookie getClearCookie() {
        Cookie cookie = new Cookie("Authorization", "");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        return cookie;
    }
}
