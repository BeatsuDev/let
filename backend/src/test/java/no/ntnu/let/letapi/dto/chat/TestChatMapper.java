package no.ntnu.let.letapi.dto.chat;

import no.ntnu.let.letapi.model.chat.Chat;
import no.ntnu.let.letapi.model.chat.Message;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TestChatMapper {
    private final ChatMapper chatMapper = new ChatMapperImpl();
    private final ChatCreationDTO chatCreationDTO = new ChatCreationDTO();
    private final MessageCreationDTO messageCreationDTO = new MessageCreationDTO();
    private final MessageDTO messageDTO = new MessageDTO();
    private final Message message = new Message();

    @Before
    public void setUp() {
        chatCreationDTO.setListingId(1L);
        messageCreationDTO.setContent("content");
        message.setId(1L);
        message.setContent("content");
        chatCreationDTO.setListingId(1L);
        messageDTO.setContent("content");
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
