package no.ntnu.let.letapi.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserMinimalDTO {
    private String email;
    private String firstName;
    private String lastName;
}
