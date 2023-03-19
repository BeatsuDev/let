package no.ntnu.let.letapi.repository.chat;

import no.ntnu.let.letapi.model.chat.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
