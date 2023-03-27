package no.ntnu.let.letapi.service;

import no.ntnu.let.letapi.model.user.User;
import no.ntnu.let.letapi.repository.user.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TestUserService {
    @MockBean
    private UserRepository userRepository;

    @Test
    public void updateUserWithNullValues() {
        User userInDb = new User();
        userInDb.setId(1L);
        userInDb.setEmail("email");
        userInDb.setFirstName("firstName");
        userInDb.setLastName("lastName");
        userInDb.setPassword("password");
        userInDb.setAdmin(true);

        userRepository = Mockito.mock(UserRepository.class);
        Mockito.when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(userInDb));
        AtomicReference<User> result = new AtomicReference<>(new User());
        Mockito.when(userRepository.save(userInDb)).then(i -> {
            result.set(i.getArgument(0));
            return i.getArgument(0);
        });

        User user = new User();
        user.setId(1L);
        new UserService(userRepository).updateUser(user);
        assertEquals(userInDb, result.get());
    }

    @Test
    public void updateUserWIthNonNullValues() {
        User userInDb = new User();
        userInDb.setId(1L);
        userInDb.setEmail("email");
        userInDb.setFirstName("firstName");
        userInDb.setLastName("lastName");
        userInDb.setPassword("password");
        userInDb.setAdmin(true);

        userRepository = Mockito.mock(UserRepository.class);
        Mockito.when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(userInDb));
        AtomicReference<User> result = new AtomicReference<>(new User());
        Mockito.when(userRepository.save(userInDb)).then(i -> {
            result.set(i.getArgument(0));
            return i.getArgument(0);
        });

        User user = new User();
        user.setId(1L);
        user.setEmail("newEmail");
        user.setFirstName("newFirstName");
        user.setLastName("newLastName");
        user.setPassword("newPassword");
        user.setAdmin(false);
        Mockito.when(userRepository.save(user)).then(i -> {
            result.set(i.getArgument(0));
            return i.getArgument(0);
        });
        new UserService(userRepository).updateUser(user);
        assertEquals(user, result.get());
    }
}
