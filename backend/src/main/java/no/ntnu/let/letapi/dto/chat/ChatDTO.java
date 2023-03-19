package no.ntnu.let.letapi.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatDTO {
    private long id;
    private long listingId;
    private long buyerId;
    private List<MessageDTO> messages;
}
