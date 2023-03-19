package no.ntnu.let.letapi.repository.listing;

import no.ntnu.let.letapi.model.listing.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
