package no.ntnu.let.letapi.dto.chat;

import no.ntnu.let.letapi.model.chat.Chat;
import no.ntnu.let.letapi.model.chat.Message;
import no.ntnu.let.letapi.model.listing.Listing;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestChatMapper {
    private ChatMapper chatMapper = new ChatMapperImpl();
    private ChatCreationDTO chatCreationDTO = new ChatCreationDTO();
    private ChatMinimalDTO chatMinimalDTO = new ChatMinimalDTO();
    private ChatFullDTO chatFullDTO = new ChatFullDTO();
    private Chat chat = new Chat();
    private MessageCreationDTO messageCreationDTO = new MessageCreationDTO();
    private MessageDTO messageDTO = new MessageDTO();
    private Message message = new Message();

    @BeforeEach
    public void resetChatCreationDTO() {
        chatCreationDTO.setListingId(1L);
    }


    @BeforeEach
    public void resetChat() {
        chat.setId(1L);
        chat.setListing(new Listing());
    }

    @BeforeEach
    public void resetMessageCreationDTO() {
        messageCreationDTO.setContent("content");
    }

    @BeforeEach
    public void resetMessage() {
        message.setId(1L);
        message.setContent("content");
    }


    @Test
    public void testFromChatCreationDTO() {
        Chat chat = chatMapper.toChat(chatCreationDTO);
        assertEquals(chatCreationDTO.getListingId(), chat.getListing().getId());
    }

    @Test
    public void testFromMessageCreationDTO() {
        Message message = chatMapper.toMessage(messageCreationDTO);
        assertEquals(messageCreationDTO.getContent(), message.getContent());
    }

    @Test
    public void testToMessageDTO() {
        MessageDTO messageDTO = chatMapper.toMessageDto(message);
        assertEquals(message.getContent(), messageDTO.getContent());
    }
}
