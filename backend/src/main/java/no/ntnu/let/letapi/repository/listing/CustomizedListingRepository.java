package no.ntnu.let.letapi.repository.listing;

import no.ntnu.let.letapi.model.listing.Listing;
import no.ntnu.let.letapi.util.ListingFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * Customized repository for listings
 */
public interface CustomizedListingRepository {
    /**
     * Find all listings matching the filter
     * @param filter The filter to use
     * @param pageRequest Information about how to paginate the results
     * @return A page of listings
     */
    Page<Listing> findAll(ListingFilter filter, PageRequest pageRequest);
}
