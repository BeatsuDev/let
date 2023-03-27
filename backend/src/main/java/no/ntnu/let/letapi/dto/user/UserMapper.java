package no.ntnu.let.letapi.dto.user;

import lombok.Setter;
import no.ntnu.let.letapi.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Mapper for users
 */
@Mapper(componentModel = "spring", injectionStrategy = org.mapstruct.InjectionStrategy.CONSTRUCTOR)
public abstract class UserMapper {
    @Setter
    @Autowired
    protected PasswordEncoder passwordEncoder;

    /**
     * Map a user to a minimal user DTO
     * @param user The user
     * @return The minimal user DTO
     */
    @Named("toMinimalDTO")
    public abstract UserMinimalDTO toMinimalDTO(User user);

    /**
     * Map a user to a full user DTO
     * @param user The user
     * @return The full user DTO
     */
    public abstract UserFullDTO toFullDTO(User user);

    /**
     * Wrapper for password encoder to be used in mapstruct
     * @param password The password
     * @return The encrypted password
     */
    @Named("encryptPassword")
    public String encryptPassword(String password) {
        if (password == null) return null;
        return passwordEncoder.encode(password);
    }

    /**
     * Map a user creation DTO to a user
     * @param userCreationDTO The user creation DTO
     * @return The user
     */
    @Mappings({
            @Mapping(target = "password", source = "password", qualifiedByName = "encryptPassword"),
            @Mapping(target = "admin", constant = "false")
    })
    public abstract User toUser(UserCreationDTO userCreationDTO);

    /**
     * Map a user update DTO to a user
     * @param userUpdateDTO The user update DTO
     * @return The user
     */
    @Mappings({
            @Mapping(target = "password", source = "password", qualifiedByName = "encryptPassword")
    })
    public abstract User toUser(UserUpdateDTO userUpdateDTO);
}
