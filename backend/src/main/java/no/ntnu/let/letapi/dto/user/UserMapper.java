package no.ntnu.let.letapi.dto.user;

import no.ntnu.let.letapi.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Named("toMinimalDTO")
    public abstract UserMinimalDTO toMinimalDTO(User user);
    public abstract UserFullDTO toFullDTO(User user);

    @Named("encryptPassword")
    public String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }
    @Mappings({
            @Mapping(target = "password", source = "password", qualifiedByName = "encryptPassword"),
            @Mapping(target = "admin", constant = "false")
    })
    public abstract User toUser(UserCreationDTO userCreationDTO);
    @Mappings({
            @Mapping(target = "password", source = "password", qualifiedByName = "encryptPassword")
    })
    public abstract User toUser(UserUpdateDTO userUpdateDTO);
}
