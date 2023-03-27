package no.ntnu.let.letapi.repository.listing;

import no.ntnu.let.letapi.model.listing.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for categories
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
