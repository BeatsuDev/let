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
import no.ntnu.let.letapi.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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

        return ResponseEntity.ok(userMapper.toFullDTO(user));
    }

    @PutMapping
    public ResponseEntity<Object> updateUser(@RequestBody UserUpdateDTO userDTO) {
        User user = userMapper.toUser(userDTO);
        user = userService.updateUser(user);
        return ResponseEntity.ok(userMapper.toFullDTO(user));
    }

    @GetMapping("/session")
    public ResponseEntity<Object> getUsers(@RequestHeader("Authorization") String token) {
        User authenTicatedUser = authenticationService.getUser(token);
        return ResponseEntity.ok(userMapper.toFullDTO(authenTicatedUser));
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

        // Set cookie with token
        HttpHeaders headers = new HttpHeaders();
        headers.set("Set-Cookie", "authorization=" + token);
        headers.set("Expires", authenticationService.getExpirationDate(token).toString());

        return ResponseEntity.ok().headers(headers).body(userMapper.toFullDTO(user));
    }

    @DeleteMapping("/session")
    public ResponseEntity<Object> logOutUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Set-Cookie", "authorization=; expires=Thu, 01 Jan 1970 00:00:00 GMT");
        return ResponseEntity.ok().headers(headers).build();
    }

    @PutMapping("/session")
    public ResponseEntity<Object> renewSession(@RequestHeader("Authorization") String token) {
        String newToken = authenticationService.renewToken(token);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Set-Cookie", "authorization=" + token);
        headers.set("Expires", authenticationService.getExpirationDate(token).toString());

        return ResponseEntity.ok().headers(headers).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(userMapper.toFullDTO(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }
}
