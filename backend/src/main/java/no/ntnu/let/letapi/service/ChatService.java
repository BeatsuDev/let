package no.ntnu.let.letapi.service;

import lombok.RequiredArgsConstructor;
import no.ntnu.let.letapi.model.chat.Chat;
import no.ntnu.let.letapi.model.chat.Message;
import no.ntnu.let.letapi.model.chat.Sender;
import no.ntnu.let.letapi.model.listing.Listing;
import no.ntnu.let.letapi.model.user.User;
import no.ntnu.let.letapi.repository.chat.ChatRepository;
import no.ntnu.let.letapi.repository.chat.MessageRepository;
import no.ntnu.let.letapi.util.DateUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    public List<Message> getMessages(long id) {
        Chat chat = chatRepository.getReferenceById(id);
        return getMessages(chat);
    }

    public List<Message> getMessages(Chat chat) {
        if (chat == null) return List.of();
        return messageRepository.getMessagesByChatOrderByTimestampDesc(chat);
    }



    public Chat getChat(long id) {
        return chatRepository.findById(id).orElse(null);
    }

    public void addMessage(Message message, long chatId) {
        Chat chat = chatRepository.getReferenceById(chatId);
        message.setTimestamp(DateUtil.getNow());
        message.setChat(chat);
        messageRepository.save(message);
    }

    public List<Chat> getChats(User user) {
        return chatRepository.getChatsByUser(user);
    }

    public Chat createChat(Listing listing, User user) {
        Chat chat = new Chat();
        chat.setListing(listing);
        chat.setBuyer(user);
        return chatRepository.save(chat);
    }

    public Message getLatestMessage(Chat chat) {
        if (chat == null) return null;
        return messageRepository.findFirstByChatOrderByTimestampDesc(chat).orElse(null);
    }
}