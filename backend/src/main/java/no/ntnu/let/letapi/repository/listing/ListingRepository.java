package no.ntnu.let.letapi.repository.listing;

import no.ntnu.let.letapi.model.listing.Listing;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for listings
 */
public interface ListingRepository extends JpaRepository<Listing, Long>, CustomizedListingRepository {
}
