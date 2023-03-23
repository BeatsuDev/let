package no.ntnu.let.letapi.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDTO extends UserCreationDTO {
    private long id;
    private Boolean admin;
}
