package no.ntnu.let.letapi.service;

import lombok.RequiredArgsConstructor;
import no.ntnu.let.letapi.model.listing.Listing;
import no.ntnu.let.letapi.model.user.User;
import no.ntnu.let.letapi.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public User createUser(User user) {
        return repository.save(user);
    }

    public User updateUser(User user) {
        User oldUser = repository.findById(user.getId()).orElse(null);
        if (oldUser == null) throw new IllegalArgumentException("User does not exist in database");

        if (user.getEmail() == null) user.setEmail(oldUser.getEmail());
        if (user.getFirstName() == null) user.setFirstName(oldUser.getFirstName());
        if (user.getLastName() == null) user.setLastName(oldUser.getLastName());
        if (user.getPassword() == null) user.setPassword(oldUser.getPassword());
        if (user.getAdmin() == null) user.setAdmin(oldUser.getAdmin());

        return repository.save(user);
    }

    public User getUserById(long id) {
        return repository.findById(id).orElse(null);
    }

    public User getUserByEmail(String email) {
        return repository.findByEmail(email);
    }

    public void deleteUserById(long id) {
        repository.deleteById(id);
    }

    public void favoriteListing(User user, Listing listing) {
        user.getFavorites().add(listing);
        repository.save(user);
    }

    public void unfavoriteListing(User user, Listing listing) {
        user.getFavorites().removeIf(l -> Objects.equals(l.getId(), listing.getId()));
        repository.save(user);
    }

    public boolean isListingFavorited(User user, Listing listing) {
        return user.getFavorites().stream().anyMatch(l -> Objects.equals(l.getId(), listing.getId()));
    }
}
