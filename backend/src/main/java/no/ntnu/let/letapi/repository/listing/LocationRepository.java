package no.ntnu.let.letapi.repository.listing;

import no.ntnu.let.letapi.model.listing.Location;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for locations
 */
public interface LocationRepository extends JpaRepository<Location, Long> {
}
