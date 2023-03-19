package no.ntnu.let.letapi.repository;

import no.ntnu.let.letapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
