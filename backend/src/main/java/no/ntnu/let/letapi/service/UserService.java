package no.ntnu.let.letapi.service;

import lombok.RequiredArgsConstructor;
import no.ntnu.let.letapi.model.listing.Listing;
import no.ntnu.let.letapi.model.user.User;
import no.ntnu.let.letapi.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Service for users
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public User createUser(User user) {
        return repository.save(user);
    }

    /**
     * Update a user
     * @param user The user to update
     * @return The updated user
     */
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

    /**
     * Get a user by id
     * @param id The id of the user
     * @return The user
     */
    public User getUserById(long id) {
        return repository.findById(id).orElse(null);
    }

    /**
     * Get a user by email
     * @param email The email of the user
     * @return The user
     */
    public User getUserByEmail(String email) {
        return repository.findByEmail(email);
    }

    /**
     * Delete a user by id
     * @param id The id of the user
     */
    public void deleteUserById(long id) {
        repository.deleteById(id);
    }

    /**
     * Favorite a listing for a user
     * @param user The user to update the favorites for
     * @param listing The listing to favorite
     */
    public void favoriteListing(User user, Listing listing) {
        user.getFavorites().add(listing);
        repository.save(user);
    }

    /**
     * Remove a listing from a user's favorites
     * @param user The user to update the favorites for
     * @param listing The listing to remove from favorites
     */
    public void unfavoriteListing(User user, Listing listing) {
        user.getFavorites().removeIf(l -> Objects.equals(l.getId(), listing.getId()));
        repository.save(user);
    }

    /**
     * Check if a listing is favorited by a user
     * @param user The user to check
     * @param listing The listing to check
     * @return True if the listing is favorited by the user
     */
    public boolean isListingFavorited(User user, Listing listing) {
        return user.getFavorites().stream().anyMatch(l -> Objects.equals(l.getId(), listing.getId()));
    }
}
