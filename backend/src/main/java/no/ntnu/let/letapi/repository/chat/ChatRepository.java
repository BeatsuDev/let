package no.ntnu.let.letapi.repository.chat;

import no.ntnu.let.letapi.model.chat.Chat;
import no.ntnu.let.letapi.model.listing.Listing;
import no.ntnu.let.letapi.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for chats
 */
public interface ChatRepository extends JpaRepository<Chat, Long>, CustomizedChatRepository {
    Optional<Chat> findByListingAndBuyer(Listing listing, User buyer);
}
