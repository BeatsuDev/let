package no.ntnu.let.letapi.controller.user;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import no.ntnu.let.letapi.dto.user.LoginDTO;
import no.ntnu.let.letapi.dto.user.UserCreationDTO;
import no.ntnu.let.letapi.dto.user.UserMapper;
import no.ntnu.let.letapi.dto.user.UserUpdateDTO;
import no.ntnu.let.letapi.model.user.User;
import no.ntnu.let.letapi.security.AuthenticationService;
import no.ntnu.let.letapi.security.CookieFactory;
import no.ntnu.let.letapi.security.UserAuthentication;
import no.ntnu.let.letapi.security.UserDetailsImpl;
import no.ntnu.let.letapi.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * User controller
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final AuthenticationManager authenticationManager;
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * Register a new user and log them in
     * @param userDTO Information to create the user
     * @param response The http response. This is used to set the authorization cookie on the client
     * @return Information about the created user
     */
    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody UserCreationDTO userDTO, HttpServletResponse response) {
        User user = userMapper.toUser(userDTO);
        if (userService.getUserByEmail(user.getEmail()) != null) {
            logger.info("Failed to create user with email " + user.getEmail() + ": Email already in use");
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        try {
            logger.debug("Creating user with email " + user.getEmail());
            user = userService.createUser(user);
        } catch (Exception e) {
            logger.warn("Failed to create user with email " + user.getEmail() + ": " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please supply all necessary fields");
        }

        logger.info("User created with email " + user.getEmail());
        UserDetails userDetails = new UserDetailsImpl(user);
        String token = authenticationService.generateToken(new UserAuthentication(userDetails));

        response.addCookie(CookieFactory.getAuthorizationCookie(token));
        return ResponseEntity.ok(userMapper.toFullDTO(user));
    }

    /**
     * Update the details of a user
     * @param userDTO Information to update the user with
     * @return The updated information about the user
     */
    @PutMapping
    public ResponseEntity<Object> updateUser(@RequestBody UserUpdateDTO userDTO) {
        Boolean selfOrAdmin = authenticationService.isAdminOrAllowed(user -> user.getId() == userDTO.getId());
        if (selfOrAdmin == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if (!selfOrAdmin) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        // If the user is not an admin, they cannot change their admin status
        if (!authenticationService.isAdmin()) userDTO.setAdmin(null);
        logger.debug("Updating user with id " + userDTO.getId());

        User user = userMapper.toUser(userDTO);
        user = userService.updateUser(user);
        logger.info("User updated with id " + user.getId());
        return ResponseEntity.ok(userMapper.toFullDTO(user));
    }

    /**
     * Get the current logged-in user
     * @return Information about the current logged-in user
     */
    @GetMapping("/session")
    public ResponseEntity<Object> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        logger.info("Getting user with email " + authentication.getName());
        User user = userService.getUserByEmail(authentication.getName());
        return ResponseEntity.ok(userMapper.toFullDTO(user));
    }

    /**
     * Log a user in and set the authorization cookie
     * @param loginDTO User credentials
     * @param response The http response. This is used to set the authorization cookie on the client
     * @return Information about the logged-in user
     */
    @PostMapping("/session")
    public ResponseEntity<Object> logInUser(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        logger.info("User logged in with email " + loginDTO.getEmail());
        User user = userService.getUserByEmail(loginDTO.getEmail());
        String token = authenticationService.generateToken(authentication);
        response.addCookie(CookieFactory.getAuthorizationCookie(token));

        return ResponseEntity.ok(userMapper.toFullDTO(user));
    }

    /**
     * Log a user out and remove the authorization cookie
     * @param response The http response. This is used to remove the authorization cookie on the client
     * @return An empty ok response
     */
    @DeleteMapping("/session")
    public ResponseEntity<Object> logOutUser(HttpServletResponse response) {
        Cookie cookie = CookieFactory.getClearCookie();
        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }

    /**
     * Renew the authorization cookie
     * @return An empty ok response
     */
    @PutMapping("/session")
    public ResponseEntity<Object> renewSession() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok().build();
    }

    /**
     * Get a user by id
     * @param id The id of the user
     * @return Information about the user
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(userMapper.toFullDTO(user));
    }

    /**
     * Delete a user
     * @return no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable long id) {
        Boolean selfOrAdmin = authenticationService.isAdminOrAllowed(user -> user.getId() == id);
        if (selfOrAdmin == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if (!selfOrAdmin) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        logger.info("Deleting user with id " + id);
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
