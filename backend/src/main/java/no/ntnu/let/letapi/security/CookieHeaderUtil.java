package no.ntnu.let.letapi.security;

import org.springframework.http.HttpHeaders;

public class CookieHeaderUtil {
    public static HttpHeaders appendCookieHeaders(HttpHeaders headers, String cookieName, String cookieValue) {
        headers.add("Set-Cookie", cookieName + "=" + cookieValue + "; Path=/; HttpOnly");
        return headers;
    }

    public static HttpHeaders appendAuthorizationHeaders(HttpHeaders headers, String token, String expiry) {
        appendCookieHeaders(headers, "Authorization", token);
        appendCookieHeaders(headers, "Expires", expiry);
        return headers;
    }
}
