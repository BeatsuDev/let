package no.ntnu.let.letapi.service;

import lombok.RequiredArgsConstructor;
import no.ntnu.let.letapi.model.listing.Listing;
import no.ntnu.let.letapi.model.listing.ListingState;
import no.ntnu.let.letapi.model.user.User;
import no.ntnu.let.letapi.repository.listing.ListingRepository;
import no.ntnu.let.letapi.repository.listing.LocationRepository;
import no.ntnu.let.letapi.util.DateUtil;
import no.ntnu.let.letapi.util.ListingFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ListingService {
    private final ListingRepository listingRepository;
    private final LocationRepository locationRepository;
    private final UserService userService;

    public Page<Listing> getListings(ListingFilter filter, PageRequest pageRequest) {
        return listingRepository.findAll(filter, pageRequest);
    }

    public Listing createListing(Listing listing, String sellerEmail) {
        User seller = userService.getUserByEmail(sellerEmail);
        listing.setSeller(seller);
        return createListing(listing);
    }

    public Listing createListing(Listing listing) {
        Date created = DateUtil.getNow();
        listing.setCreated(created);

        if (listing.getLocation().getId() == null)
            listing.setLocation(locationRepository.save(listing.getLocation()));
        if (listing.getState() == null)
            listing.setState(ListingState.ACTIVE);

        listing = listingRepository.save(listing);
        return listing;
    }

    public Listing updateListing(Listing listing) {
        Listing oldListing = listingRepository.findById(listing.getId()).orElse(null);
        if (oldListing == null) throw new IllegalArgumentException("Listing does not exist in database");

        if (listing.getState() == ListingState.SOLD) listing.setSold(DateUtil.getNow());
        if (listing.getTitle() == null) listing.setTitle(oldListing.getTitle());
        if (listing.getSummary() == null) listing.setSummary(oldListing.getSummary());
        if (listing.getDescription() == null) listing.setDescription(oldListing.getDescription());
        if (listing.getCategory() == null) listing.setCategory(oldListing.getCategory());
        if (listing.getLocation() == null) listing.setLocation(oldListing.getLocation());
        if (listing.getGallery() == null) listing.setGallery(oldListing.getGallery());
        if (listing.getState() == null) listing.setState(oldListing.getState());
        if (listing.getSeller() == null) listing.setSeller(oldListing.getSeller());

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
