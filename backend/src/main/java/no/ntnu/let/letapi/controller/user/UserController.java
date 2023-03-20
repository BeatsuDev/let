package no.ntnu.let.letapi.controller.user;

import no.ntnu.let.letapi.dto.user.LoginDTO;
import no.ntnu.let.letapi.dto.user.UserCreationDTO;
import no.ntnu.let.letapi.dto.user.UserUpdateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody UserCreationDTO userDTO) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
    @GetMapping
    public ResponseEntity<Object> updateUser(@RequestBody UserUpdateDTO userDTO) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PostMapping("/session")
    public ResponseEntity<Object> logInUser(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @DeleteMapping("/session")
    public ResponseEntity<Object> logOutUser() {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PutMapping("/session")
    public ResponseEntity<Object> renewSession() {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}
