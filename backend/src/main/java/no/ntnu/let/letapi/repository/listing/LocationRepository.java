package no.ntnu.let.letapi.repository.listing;

import no.ntnu.let.letapi.model.listing.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
