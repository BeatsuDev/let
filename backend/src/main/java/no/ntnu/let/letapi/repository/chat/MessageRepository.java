package no.ntnu.let.letapi.repository.chat;

import no.ntnu.let.letapi.model.chat.Chat;
import no.ntnu.let.letapi.model.chat.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> getMessagesByChatOrderByTimestampDesc(Chat chat);

    Optional<Message> findFirstByChatOrderByTimestampDesc(Chat chat);
}
