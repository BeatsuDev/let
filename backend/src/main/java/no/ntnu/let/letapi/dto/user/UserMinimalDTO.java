package no.ntnu.let.letapi.dto.user;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO for giving a user's minimal information
 */
@Getter
@Setter
public class UserMinimalDTO {
    private String email;
    private String firstName;
    private String lastName;
}
