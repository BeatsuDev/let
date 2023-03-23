package no.ntnu.let.letapi.security;

import jakarta.servlet.http.Cookie;

public class CookieFactory {
    public static Cookie getAuthorizationCookie(String token) {
        Cookie cookie = new Cookie("Authorization", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        return cookie;
    }
}
