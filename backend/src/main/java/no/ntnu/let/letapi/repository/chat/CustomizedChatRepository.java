package no.ntnu.let.letapi.repository.chat;

import no.ntnu.let.letapi.model.chat.Chat;
import no.ntnu.let.letapi.model.user.User;

import java.util.List;

public interface CustomizedChatRepository {
    List<Chat> getChatsByUser(User user);
}
