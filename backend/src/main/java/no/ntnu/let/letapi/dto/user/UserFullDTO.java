package no.ntnu.let.letapi.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFullDTO extends UserMinimalDTO {
    private long id;
    private boolean admin;
}
