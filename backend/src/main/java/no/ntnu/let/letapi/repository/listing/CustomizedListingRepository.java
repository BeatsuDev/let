package no.ntnu.let.letapi.repository.listing;

import no.ntnu.let.letapi.model.listing.Listing;
import no.ntnu.let.letapi.util.ListingFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface CustomizedListingRepository {
    Page<Listing> findAll(ListingFilter filter, PageRequest pageRequest);
}
