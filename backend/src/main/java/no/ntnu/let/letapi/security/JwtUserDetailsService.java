package no.ntnu.let.letapi.security;

import lombok.RequiredArgsConstructor;
import no.ntnu.let.letapi.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service for user details
 */
@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
    private final UserService userService;

    /**
     * Load user details by username
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never null)
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserDetailsImpl(userService.getUserByEmail(username));
    }
}
