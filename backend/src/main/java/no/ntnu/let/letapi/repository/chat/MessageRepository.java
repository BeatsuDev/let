package no.ntnu.let.letapi.repository.chat;

import no.ntnu.let.letapi.model.chat.Chat;
import no.ntnu.let.letapi.model.chat.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for messages
 */
public interface MessageRepository extends JpaRepository<Message, Long> {
    /**
     * Get all messages for a chat
     * @param chat The chat to get messages for
     * @return A list of messages
     */
    List<Message> getMessagesByChatOrderByTimestampDesc(Chat chat);

    /**
     * Get the latest message for a chat
     * @param chat The chat to get the latest message for
     * @return The latest message
     */
    Optional<Message> findFirstByChatOrderByTimestampDesc(Chat chat);
}
