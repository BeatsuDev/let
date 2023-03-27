package no.ntnu.let.letapi.dto.user;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO for giving a user's full information
 */
@Getter
@Setter
public class UserFullDTO extends UserMinimalDTO {
    private long id;
    private boolean admin;
}
