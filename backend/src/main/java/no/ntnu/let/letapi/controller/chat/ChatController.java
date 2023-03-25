package no.ntnu.let.letapi.controller.chat;

import lombok.RequiredArgsConstructor;
import no.ntnu.let.letapi.dto.chat.ChatCreationDTO;
import no.ntnu.let.letapi.dto.chat.ChatMapper;
import no.ntnu.let.letapi.dto.chat.ChatMinimalDTO;
import no.ntnu.let.letapi.dto.chat.MessageCreationDTO;
import no.ntnu.let.letapi.model.chat.Chat;
import no.ntnu.let.letapi.model.chat.Message;
import no.ntnu.let.letapi.model.chat.Sender;
import no.ntnu.let.letapi.model.listing.Listing;
import no.ntnu.let.letapi.model.user.User;
import no.ntnu.let.letapi.security.AuthenticationService;
import no.ntnu.let.letapi.service.ChatService;
import no.ntnu.let.letapi.service.ListingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final ListingService listingService;
    private final AuthenticationService authenticationService;
    private final ChatMapper chatMapper;

    private final Sender SELLER = Sender.SELLER;
    private final Sender BUYER = Sender.BUYER;

    @GetMapping
    public ResponseEntity<Object> getChats() {
        User user = authenticationService.getLoggedInUser();
        if (user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        List<ChatMinimalDTO> chats = chatService.getChats(user).stream()
                .map(chat -> chatMapper.toChatMinimalDto(chat, chatService.getLatestMessage(chat))).toList();

        if (chats.isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.ok(chats);
    }

    @PostMapping
    public ResponseEntity<Object> createChat(@RequestBody ChatCreationDTO chatCreation) {
        Listing listing = listingService.getListing(chatCreation.getListingId());
        if (listing == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Listing not found");

        User user = authenticationService.getLoggedInUser();
        if (user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if (user.getId() == listing.getSeller().getId())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You cannot chat with yourself");

        Chat chat = chatService.createChat(listing, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(chatMapper.toChatFullDto(chat, List.of()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getChat(@PathVariable long id) {
        Chat chat = chatService.getChat(id);
        if (chat == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        // Only the buyer and seller of the listing (or admins) can access the chat
        Boolean adminOrOwner = authenticationService
                .isAdminOrAllowed(user ->
                        chat.getBuyer().getId() == user.getId() ||
                        chat.getListing().getSeller().getId() == user.getId());
        if (adminOrOwner == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if (!adminOrOwner) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        List<Message> messages = chatService.getMessages(chat);
        return ResponseEntity.ok(chatMapper.toChatFullDto(chat, messages));
    }

    @PostMapping("/{id}")
    public ResponseEntity<Object> sendMessage(@PathVariable long id, @RequestBody MessageCreationDTO messageCreation) {
        Chat chat = chatService.getChat(id);
        if (chat == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        // Only the buyer and seller of the listing (or admins) can access the chat
        Boolean isAllowed = authenticationService.isAllowed(user ->
                chat.getBuyer().getId() == user.getId() ||
                chat.getListing().getSeller().getId() == user.getId()
        );
        if (isAllowed == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if (!isAllowed) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        // Set message sender and add message to chat
        Message message = chatMapper.toMessage(messageCreation);
        message.setSender(authenticationService.getLoggedInUser().getId() == chat.getBuyer().getId() ? BUYER : SELLER);
        chatService.addMessage(message, id);

        List<Message> messages = chatService.getMessages(chat);
        return ResponseEntity.status(HttpStatus.CREATED).body(chatMapper.toChatFullDto(chat, messages));
    }
}
