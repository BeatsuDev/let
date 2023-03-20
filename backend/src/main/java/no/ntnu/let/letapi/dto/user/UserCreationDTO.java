package no.ntnu.let.letapi.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreationDTO {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
}
