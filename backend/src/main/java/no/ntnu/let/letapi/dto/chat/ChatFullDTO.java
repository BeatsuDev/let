package no.ntnu.let.letapi.dto.chat;

import lombok.Getter;
import lombok.Setter;
import no.ntnu.let.letapi.dto.listing.ListingMinimalDTO;
import no.ntnu.let.letapi.dto.user.UserFullDTO;
import no.ntnu.let.letapi.model.chat.Sender;

/**
 * DTO for a chat
 */
@Getter
@Setter
public class ChatFullDTO {
    private long id;
    private UserFullDTO buyer;
    private UserFullDTO seller;
    private ListingMinimalDTO listing;
    private MessageDTO[] messages;
}
