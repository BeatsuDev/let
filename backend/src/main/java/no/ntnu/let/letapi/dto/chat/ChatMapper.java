package no.ntnu.let.letapi.dto.chat;

import no.ntnu.let.letapi.dto.listing.ListingMapper;
import no.ntnu.let.letapi.dto.user.UserMapper;
import no.ntnu.let.letapi.model.chat.Chat;
import no.ntnu.let.letapi.model.chat.Message;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper for chats
 */
@Mapper(componentModel = "spring", uses = {ListingMapper.class, UserMapper.class})
public
interface ChatMapper {
    /**
     * Map a chat creation DTO to a chat
     * @param chatCreationDTO The chat creation DTO
     * @return The chat entity
     */
    @Mappings({
            @Mapping(target = "listing.id", source = "listingId"),
    })
    Chat toChat(ChatCreationDTO chatCreationDTO);

    /**
     * Map a chat to a minimal chat DTO
     * @param chat The chat
     * @param lastMessage The last message in the chat
     * @return The minimal chat DTO
     */
    @Mappings({
            @Mapping(target = "lastMessage", source = "lastMessage"),
            @Mapping(target = "id", source = "chat.id"),
            @Mapping(target = "listing", source = "chat.listing", qualifiedByName = "toMinimalListingDTO"),
            @Mapping(target = "seller", source = "chat.listing.seller"),
    })
    ChatMinimalDTO toChatMinimalDto(Chat chat, Message lastMessage);
    /**
     * Map a chat to a full chat DTO
     * @param chat The chat
     * @param messages The messages in the chat
     * @return The full chat DTO
     */
    @Mappings({
            @Mapping(target = "messages", source = "messages"),
            @Mapping(target = "id", source = "chat.id"),
            @Mapping(target = "listing", source = "chat.listing", qualifiedByName = "toMinimalListingDTO"),
            @Mapping(target = "seller", source = "chat.listing.seller"),
    })
    ChatFullDTO toChatFullDto(Chat chat, List<Message> messages);

    /**
     * Map a message creation DTO to a message
     * @param messageCreationDTO The message creation DTO
     * @return The message entity
     */
    Message toMessage(MessageCreationDTO messageCreationDTO);
    /**
     * Map a message to a message DTO
     * @param message The message
     * @return The message DTO
     */
    MessageDTO toMessageDto(Message message);
}
