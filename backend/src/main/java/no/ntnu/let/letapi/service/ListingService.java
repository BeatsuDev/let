package no.ntnu.let.letapi.service;

import no.ntnu.let.letapi.model.listing.Listing;
import no.ntnu.let.letapi.repository.listing.ListingRepository;
import no.ntnu.let.letapi.util.ListingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ListingService {
    private final ListingRepository listingRepository;

    @Autowired
    ListingService(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    public Page<Listing> getListings(ListingFilter filter, PageRequest pageRequest) {
        return listingRepository.findAll(pageRequest);
    }

    public Listing createListing(Listing listing) {
        listing = listingRepository.save(listing);
        return listing;
    }

    public Listing updateListing(Listing listing) {
        listing = listingRepository.save(listing);
        return listing;
    }

    public Listing getListing(long id) {
        return listingRepository.findById(id).orElse(null);
    }

    public void deleteListing(Listing listing) {
        listingRepository.delete(listing);
    }
}
