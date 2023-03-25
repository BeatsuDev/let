package no.ntnu.let.letapi.dto.chat;

import no.ntnu.let.letapi.dto.listing.ListingMapper;
import no.ntnu.let.letapi.dto.user.UserMapper;
import no.ntnu.let.letapi.model.chat.Chat;
import no.ntnu.let.letapi.model.chat.Message;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ListingMapper.class, UserMapper.class})
public
interface ChatMapper {
    @Mappings({
            @Mapping(target = "listing.id", source = "listingId"),
    })
    Chat toChat(ChatCreationDTO chatCreationDTO);
    @Mappings({
            @Mapping(target = "lastMessage", source = "lastMessage"),
            @Mapping(target = "id", source = "chat.id"),
            @Mapping(target = "listing", source = "chat.listing", qualifiedByName = "toMinimalListingDTO"),
            @Mapping(target = "seller", source = "chat.listing.seller"),
    })
    ChatMinimalDTO toChatMinimalDto(Chat chat, Message lastMessage);
    @Mappings({
            @Mapping(target = "messages", source = "messages"),
            @Mapping(target = "id", source = "chat.id"),
            @Mapping(target = "listing", source = "chat.listing", qualifiedByName = "toMinimalListingDTO"),
            @Mapping(target = "seller", source = "chat.listing.seller"),
    })
    ChatFullDTO toChatFullDto(Chat chat, List<Message> messages);

    Message toMessage(MessageCreationDTO messageCreationDTO);
    MessageDTO toMessageDto(Message message);
}
