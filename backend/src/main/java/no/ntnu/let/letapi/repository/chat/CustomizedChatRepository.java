package no.ntnu.let.letapi.repository.chat;

import no.ntnu.let.letapi.model.chat.Chat;
import no.ntnu.let.letapi.model.user.User;

import java.util.List;

/**
 * Customized repository for chats to fetch all chats for a user
 */
public interface CustomizedChatRepository {
    /**
     * Get all chats for a user
     * @param user The user to get chats for
     * @return A list of chats
     */
    List<Chat> getChatsByUser(User user);
}
