package no.ntnu.let.letapi.controller;

import no.ntnu.let.letapi.dto.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/user")
public class UserController {
    @PostMapping("/")
    public ResponseEntity<Object> createUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
    @GetMapping("/")
    public ResponseEntity<Object> updateUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PostMapping("/session")
    public ResponseEntity<Object> logInUser(@RequestBody UserDTO userDTO) {
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
