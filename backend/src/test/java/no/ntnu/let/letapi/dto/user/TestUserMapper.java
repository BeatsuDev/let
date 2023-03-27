package no.ntnu.let.letapi.dto.user;

import no.ntnu.let.letapi.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUserMapper {
    private UserMapper userMapper = new UserMapperImpl();
    private User user = new User();
    private UserCreationDTO userCreationDTO = new UserCreationDTO();
    private UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
    private UserMinimalDTO userMinimalDTO = new UserMinimalDTO();
    private UserFullDTO userFullDTO = new UserFullDTO();
    private String password = "password";

    @BeforeEach
    public void setUp() {
        user.setId(1L);
        user.setEmail("email");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setAdmin(true);
        userCreationDTO.setEmail("email");
        userCreationDTO.setFirstName("firstName");
        userCreationDTO.setLastName("lastName");
        userCreationDTO.setPassword(password);
        userUpdateDTO.setEmail("email");
        userUpdateDTO.setFirstName("firstName");
        userUpdateDTO.setLastName("lastName");
        userUpdateDTO.setPassword(password);
        userMinimalDTO.setEmail("email");
        userMinimalDTO.setFirstName("firstName");
        userMinimalDTO.setLastName("lastName");
        userFullDTO.setId(1L);
        userFullDTO.setEmail("email");
        userFullDTO.setFirstName("firstName");
        userFullDTO.setLastName("lastName");
        userFullDTO.setAdmin(true);
    }

    @Test
    public void testToMinimalDTO() {
        UserMinimalDTO mappedUser = userMapper.toMinimalDTO(user);
        assertEquals(userMinimalDTO.getEmail(), mappedUser.getEmail());
        assertEquals(userMinimalDTO.getFirstName(), mappedUser.getFirstName());
        assertEquals(userMinimalDTO.getLastName(), mappedUser.getLastName());
    }

    @Test
    public void testToFullDTO() {
        UserFullDTO mappedUser = userMapper.toFullDTO(user);
        assertEquals(userFullDTO.getId(), mappedUser.getId());
        assertEquals(userFullDTO.getEmail(), mappedUser.getEmail());
        assertEquals(userFullDTO.getFirstName(), mappedUser.getFirstName());
        assertEquals(userFullDTO.getLastName(), mappedUser.getLastName());
    }
}
