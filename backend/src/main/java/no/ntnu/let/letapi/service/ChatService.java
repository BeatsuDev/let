package no.ntnu.let.letapi.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import no.ntnu.let.letapi.model.chat.Chat;
import no.ntnu.let.letapi.model.chat.Message;
import no.ntnu.let.letapi.model.chat.Sender;
import no.ntnu.let.letapi.model.listing.Listing;
import no.ntnu.let.letapi.model.user.User;
import no.ntnu.let.letapi.repository.chat.ChatRepository;
import no.ntnu.let.letapi.repository.chat.MessageRepository;
import no.ntnu.let.letapi.repository.listing.ListingRepository;
import no.ntnu.let.letapi.util.DateUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for chats
 */
@Service
@RequiredArgsConstructor
public class ChatService {
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final ListingRepository listingRepository;

    /**
     * Get chat by listing and user
     * @param id The id of the listing
     * @return The chat
     */
    public List<Message> getMessages(long id) {
        Chat chat = chatRepository.getReferenceById(id);
        return getMessages(chat);
    }

    /**
     * Get messages by chat
     * @param chat The chat
     * @return The messages
     */
    public List<Message> getMessages(Chat chat) {
        if (chat == null) return List.of();
        return messageRepository.getMessagesByChatOrderByTimestampDesc(chat);
    }


    /**
     * Get a chat by id
     * @param id The id of the chat
     * @return The chat
     */
    public Chat getChat(long id) {
        return chatRepository.findById(id).orElse(null);
    }

    /**
     * Add a message to a chat
     * @param message The message to add
     * @param chatId The id of the chat
     */
    public void addMessage(Message message, long chatId) {
        Chat chat = chatRepository.getReferenceById(chatId);
        message.setTimestamp(DateUtil.getNow());
        message.setChat(chat);
        messageRepository.save(message);
    }

    /**
     * Get chats by user
     * @param user The user
     * @return The chats the user is a part of
     */
    public List<Chat> getChats(User user) {
        return chatRepository.getChatsByUser(user);
    }

    /**
     * Create a chat for a listing and user
     * @param listing The listing
     * @param user The user
     * @return The created chat
     */
    public Chat createChat(Listing listing, User user) {
        Chat chat = new Chat();
        chat.setListing(listing);
        chat.setBuyer(user);
        if (getChat(listing, user) != null) throw new IllegalArgumentException("Chat already exists");
        return chatRepository.save(chat);
    }

    /**
     * Get the latest message in a chat
     * @param chat The chat
     * @return The latest message
     */
    public Message getLatestMessage(Chat chat) {
        if (chat == null) return null;
        return messageRepository.findFirstByChatOrderByTimestampDesc(chat).orElse(null);
    }

    /**
     * Get a chat by listing id and user
     * @param ListingId The id of the listing
     * @param user The user
     * @return The chat
     */
    public Chat getChat(long ListingId, User user) {
        Listing listing = listingRepository.getReferenceById(ListingId);
        return getChat(listing, user);
    }

    /**
     * Get a chat by listing and user
     * @param listing The listing
     * @param user The user
     * @return The chat
     */
    public Chat getChat(Listing listing, User user) {
        try {
            return chatRepository.findByListingAndBuyer(listing, user).orElse(null);
        } catch (EntityNotFoundException e) {
            return null;
        }
    }
}