package no.ntnu.let.letapi.controller.chat;

import lombok.RequiredArgsConstructor;
import no.ntnu.let.letapi.dto.chat.ChatCreationDTO;
import no.ntnu.let.letapi.dto.chat.MessageCreationDTO;
import no.ntnu.let.letapi.dto.chat.MessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {
    @GetMapping
    public ResponseEntity<Object> getChats() {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PostMapping
    public ResponseEntity<Object> createChat(ChatCreationDTO chatCreation) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getChat(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PostMapping("/{id}")
    public ResponseEntity<Object> sendMessage(@PathVariable String id, MessageCreationDTO messageCreation) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}
