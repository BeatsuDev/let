package no.ntnu.let.letapi.dto.user;

import no.ntnu.let.letapi.model.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(SpringRunner.class)
public class TestUserMapper {
    private UserMapper userMapper = new UserMapperImpl();
    private User user = new User();
    private UserCreationDTO userCreationDTO = new UserCreationDTO();
    private UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
    private UserMinimalDTO userMinimalDTO = new UserMinimalDTO();
    private UserFullDTO userFullDTO = new UserFullDTO();
    private String password = "password";
    private String encodedPassword = "encodedPassword";

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Before
    public void setUp() {
        user.setId(1L);
        user.setEmail("email");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setPassword(password);
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

        Mockito.when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        userMapper.setPasswordEncoder(passwordEncoder);
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
        assertEquals(userFullDTO.isAdmin(), mappedUser.isAdmin());
    }

    @Test
    public void testFromUserCreationDTO() {
        User mappedUser = userMapper.toUser(userCreationDTO);
        assertEquals(userCreationDTO.getEmail(), mappedUser.getEmail());
        assertEquals(userCreationDTO.getFirstName(), mappedUser.getFirstName());
        assertEquals(userCreationDTO.getLastName(), mappedUser.getLastName());
        assertEquals(encodedPassword, mappedUser.getPassword());
    }

    @Test
    public void testFromUserUpdateDTO() {
        User mappedUser = userMapper.toUser(userUpdateDTO);
        assertEquals(userUpdateDTO.getEmail(), mappedUser.getEmail());
        assertEquals(userUpdateDTO.getFirstName(), mappedUser.getFirstName());
        assertEquals(userUpdateDTO.getLastName(), mappedUser.getLastName());
        assertEquals(encodedPassword, mappedUser.getPassword());
    }

    @Test
    public void testEncodePasswordNull() {
        assertNull(userMapper.encryptPassword(null));
    }
}
