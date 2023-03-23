package no.ntnu.let.letapi.controller.user;

import com.nimbusds.jose.proc.SecurityContext;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import no.ntnu.let.letapi.dto.user.LoginDTO;
import no.ntnu.let.letapi.dto.user.UserCreationDTO;
import no.ntnu.let.letapi.dto.user.UserMapper;
import no.ntnu.let.letapi.dto.user.UserUpdateDTO;
import no.ntnu.let.letapi.model.user.User;
import no.ntnu.let.letapi.security.AuthenticationService;
import no.ntnu.let.letapi.security.CookieHeaderUtil;
import no.ntnu.let.letapi.security.UserAuthentication;
import no.ntnu.let.letapi.security.UserDetailsImpl;
import no.ntnu.let.letapi.service.UserService;
import org.springframework.http.HttpHeaders;
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
    public ResponseEntity<Object> createUser(@RequestBody UserCreationDTO userDTO) {
        User user = userMapper.toUser(userDTO);
        user = userService.createUser(user);

        UserDetails userDetails = new UserDetailsImpl(user);
        String token = authenticationService.generateToken(new UserAuthentication(userDetails));

        HttpHeaders headers = CookieHeaderUtil.appendAuthorizationHeaders(
                new HttpHeaders(),
                token,
                authenticationService.getExpirationDate(token).toString()
        );
        return ResponseEntity.ok().headers(headers).body(userMapper.toFullDTO(user));
    }

    @PutMapping
    public ResponseEntity<Object> updateUser(@RequestBody UserUpdateDTO userDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (userDetails.getId() != userDTO.getId()) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        User user = userMapper.toUser(userDTO);
        user = userService.updateUser(user);
        return ResponseEntity.ok(userMapper.toFullDTO(user));
    }

    @GetMapping("/session")
    public ResponseEntity<Object> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());

        return ResponseEntity.ok(userMapper.toFullDTO(user));
    }

    @PostMapping("/session")
    public ResponseEntity<Object> logInUser(@RequestBody LoginDTO loginDTO) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = userService.getUserByEmail(loginDTO.getEmail());
        String token = authenticationService.generateToken(authentication);

        HttpHeaders headers = CookieHeaderUtil.appendAuthorizationHeaders(
                new HttpHeaders(),
                token,
                authenticationService.getExpirationDate(token).toString()
        );

        return ResponseEntity.ok().headers(headers).body(userMapper.toFullDTO(user));
    }

    @DeleteMapping("/session")
    public ResponseEntity<Object> logOutUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Set-Cookie", "authorization=; expires=Thu, 01 Jan 1970 00:00:00 GMT");
        return ResponseEntity.ok().headers(headers).build();
    }

    @PutMapping("/session")
    public ResponseEntity<Object> renewSession() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        String token = authenticationService.generateToken(authentication);
        HttpHeaders headers = CookieHeaderUtil.appendAuthorizationHeaders(
                new HttpHeaders(),
                token,
                authenticationService.getExpirationDate(token).toString()
        );

        return ResponseEntity.ok().headers(headers).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(userMapper.toFullDTO(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (userDetails.getId() != id && !userDetails.isAdmin()) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
