package no.ntnu.let.letapi.controller.listing;

import no.ntnu.let.letapi.dto.listing.*;
import no.ntnu.let.letapi.model.listing.Listing;
import no.ntnu.let.letapi.model.listing.ListingState;
import no.ntnu.let.letapi.service.ListingService;
import no.ntnu.let.letapi.util.ListingFilter;
import no.ntnu.let.letapi.util.ListingFilterBuilder;
import no.ntnu.let.letapi.util.UrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/listing")
public class ListingController {
    private final ListingService listingService;
    private final ListingMapper mapper;
    private final String BASE_URL = UrlUtil.getBaseUrl() + "/listing";

    @Autowired
    ListingController(ListingMapper mapper, ListingService listingService) {
        this.mapper = mapper;
        this.listingService = listingService;
    }

    @GetMapping
    public ResponseEntity<Object> getListings(
            @RequestParam(required = false) String searchString,
            @RequestParam(required = false) LocationDTO locationDTO,
            @RequestParam(required = false) Integer radius,
            @RequestParam(required = false) List<Integer> categories,
            @RequestParam(required = false) Boolean favorites,
            @RequestParam(required = false) List<ListingState> states,
            @RequestParam(defaultValue = "1", required = false) Integer page,
            @RequestParam(defaultValue = "50", required = false) Integer pageSize
            ) {
        // Validate the page number
        if (page <= 0) return ResponseEntity.badRequest().body("Page number must be greater than 0");

        // Build the filter
        ListingFilterBuilder filterBuilder = new ListingFilterBuilder();
        filterBuilder
                .searchString(searchString)
                .locationRadius(locationDTO, radius)
                .categories(categories)
                .favorites(favorites)
                .states(states);
        ListingFilter filter = filterBuilder.build();

        // Get the listings from the database
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
        Page<Listing> listings = listingService.getListings(filterBuilder.build(), pageRequest);

        // If no listings matched the criteria, return a 204 No Content
        if (listings.isEmpty()) return ResponseEntity.noContent().build();

        // Build the next and previous page URLs
        String urlParameters = filter.toUrlParameters();
        String requestUrl = BASE_URL + urlParameters;
        requestUrl += requestUrl.contains("?") ? "&" : "?";
        String nextUrl = null;
        String prevUrl = null;
        if (listings.hasNext()) {
            nextUrl = requestUrl + "page=" + (page + 1) + "&pageSize=" + pageSize;
        }
        if (listings.hasPrevious()) {
            prevUrl = requestUrl + "page=" + (page - 1) + "&pageSize=" + pageSize;
        }

        // Convert the listings to DTOs and return them
        List<ListingMinimalDTO> listingsDTO = listings.getContent().stream().map(mapper::toListingMinimalDTO).toList();
        var pagedListings = new PagedListingsDTO(
                listingsDTO,
                listings.getNumber() + 1,
                listings.getTotalPages(),
                nextUrl,
                prevUrl
        );
        return ResponseEntity.ok(pagedListings);
    }

    @PostMapping
    public ResponseEntity<Object> createListing(@RequestBody ListingCreationDTO listingCreationDTO) {
        Listing listing = mapper.toListing(listingCreationDTO);
        Listing savedListing = listingService.createListing(listing);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toListingMinimalDTO(savedListing));
    }

    @PutMapping
    public ResponseEntity<Object> updateListing(@RequestBody ListingUpdateDTO listingDTO) {
        Listing listing = mapper.toListing(listingDTO);
        Listing savedListing = listingService.updateListing(listing);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toListingMinimalDTO(savedListing));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getListing(@PathVariable long id) {
        Listing listing = listingService.getListing(id);
        if (listing == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Listing not found");
        }

        return ResponseEntity.ok(mapper.toListingFullDTO(listing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteListing(@PathVariable long id) {
        Listing listing = listingService.getListing(id);
        if (listing == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Listing not found");
        }

        listingService.deleteListing(listing);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Listing deleted");
    }
}
