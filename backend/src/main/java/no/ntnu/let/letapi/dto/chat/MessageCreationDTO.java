package no.ntnu.let.letapi.dto.chat;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO for creating a message
 */
@Getter
@Setter
public class MessageCreationDTO {
    private String content;
}
