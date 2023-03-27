package no.ntnu.let.letapi.dto.user;

import lombok.Getter;
import lombok.Setter;

/**
 * Authentication DTO
 */
@Getter
@Setter
public class LoginDTO {
    private String email;
    private String password;
}
