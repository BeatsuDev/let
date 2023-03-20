package no.ntnu.let.letapi.repository.user;

import no.ntnu.let.letapi.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
