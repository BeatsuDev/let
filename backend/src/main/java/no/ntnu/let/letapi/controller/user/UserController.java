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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final AuthenticationManager authenticationManager;
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody UserCreationDTO userDTO, HttpServletResponse response) {
        User user = userMapper.toUser(userDTO);
        if (userService.getUserByEmail(user.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        try {
            user = userService.createUser(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please supply all necessary fields");
        }

        UserDetails userDetails = new UserDetailsImpl(user);
        String token = authenticationService.generateToken(new UserAuthentication(userDetails));

        response.addCookie(CookieFactory.getAuthorizationCookie(token));
        return ResponseEntity.ok(userMapper.toFullDTO(user));
    }

    @PutMapping
    public ResponseEntity<Object> updateUser(@RequestBody UserUpdateDTO userDTO) {
        Boolean selfOrAdmin = authenticationService.isAdminOrAllowed(user -> user.getId() == userDTO.getId());
        if (selfOrAdmin == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if (!selfOrAdmin) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        // If the user is not an admin, they cannot change their admin status
        if (!authenticationService.isAdmin()) userDTO.setAdmin(null);

        User user = userMapper.toUser(userDTO);
        user = userService.updateUser(user);
        return ResponseEntity.ok(userMapper.toFullDTO(user));
    }

    @GetMapping("/session")
    public ResponseEntity<Object> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        User user = userService.getUserByEmail(authentication.getName());
        return ResponseEntity.ok(userMapper.toFullDTO(user));
    }

    @PostMapping("/session")
    public ResponseEntity<Object> logInUser(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = userService.getUserByEmail(loginDTO.getEmail());
        String token = authenticationService.generateToken(authentication);
        response.addCookie(CookieFactory.getAuthorizationCookie(token));

        return ResponseEntity.ok(userMapper.toFullDTO(user));
    }

    @DeleteMapping("/session")
    public ResponseEntity<Object> logOutUser(HttpServletResponse response) {
        Cookie cookie = new Cookie("Authorization", null);
        cookie.setMaxAge(0);

        response.reset();
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/session")
    public ResponseEntity<Object> renewSession(HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        String token = authenticationService.generateToken(authentication);
        response.addCookie(CookieFactory.getAuthorizationCookie(token));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(userMapper.toFullDTO(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable long id) {
        Boolean selfOrAdmin = authenticationService.isAdminOrAllowed(user -> user.getId() == id);
        if (selfOrAdmin == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if (!selfOrAdmin) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
