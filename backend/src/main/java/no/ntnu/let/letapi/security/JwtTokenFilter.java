package no.ntnu.let.letapi.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import no.ntnu.let.letapi.model.user.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    private final AuthenticationService authenticationService;

    JwtTokenFilter(@Lazy AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            Arrays.stream(cookies)
                    .filter(cookie -> cookie.getName().equals("Authorization"))
                    .findFirst().ifPresent(cookie -> {
                        try {
                            SecurityContextHolder.getContext().setAuthentication(authenticationService.getAuthentication(cookie.getValue()));
                            response.addCookie(CookieFactory.getAuthorizationCookie(authenticationService.renewToken(cookie.getValue())));
                        } catch (Exception e) {
                            SecurityContextHolder.clearContext();
                        }
                    });
        } else {
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}
