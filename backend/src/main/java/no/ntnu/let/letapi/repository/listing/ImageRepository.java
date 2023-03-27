package no.ntnu.let.letapi.repository.listing;

import no.ntnu.let.letapi.model.listing.Image;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for images
 */
public interface ImageRepository extends JpaRepository<Image, Long> {
}
