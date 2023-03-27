package no.ntnu.let.letapi.repository.user;

import no.ntnu.let.letapi.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for users
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find a user by email
     * @param email The email to search for
     * @return The user with the given email
     */
    User findByEmail(String email);
}
