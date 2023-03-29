package no.ntnu.let.letapi.controller.listing;

import lombok.RequiredArgsConstructor;
import no.ntnu.let.letapi.dto.listing.*;
import no.ntnu.let.letapi.model.listing.Listing;
import no.ntnu.let.letapi.model.listing.ListingState;
import no.ntnu.let.letapi.model.user.User;
import no.ntnu.let.letapi.security.AuthenticationService;
import no.ntnu.let.letapi.service.CategoryService;
import no.ntnu.let.letapi.service.ListingService;
import no.ntnu.let.letapi.service.UserService;
import no.ntnu.let.letapi.util.ListingFilter;
import no.ntnu.let.letapi.util.ListingFilter.ListingFilterBuilder;
import no.ntnu.let.letapi.util.UrlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Controller for listing endpoints
 */
@RestController
@RequestMapping("/listing")
@RequiredArgsConstructor
public class ListingController {
    private final ListingService listingService;
    private final ListingMapper mapper;
    private final UserService userService;
    private final CategoryService categoryService;
    private final AuthenticationService authenticationService;
    private final String BASE_URL = UrlUtil.getBaseUrl() + "/listing";
    private final Logger logger = LoggerFactory.getLogger(ListingController.class);

    /**
     * Filter listing and return paginated matches
     * @param searchString Search string
     * @param longitude Longitude
     * @param latitude Latitude
     * @param radius Radius
     * @param categories Categories
     * @param user User ID
     * @param favorites Whether to only show favorites
     * @param state Listing states to filter by
     * @param page  Page number
     * @param pageSize Page size
     * @return List of listings (paginated)
     */
    @GetMapping
    public ResponseEntity<Object> getListings(
            @RequestParam(required = false) String searchString,
            @RequestParam(required = false) Double longitude,
            @RequestParam(required = false) Double latitude,
            @RequestParam(required = false) Integer radius,
            @RequestParam(required = false) List<Integer> categories,
            @RequestParam(required = false) Long user,
            @RequestParam(required = false) Boolean favorites,
            @RequestParam(required = false) List<ListingState> state,
            @RequestParam(defaultValue = "1", required = false) Integer page,
            @RequestParam(defaultValue = "50", required = false) Integer pageSize
    ) {
        // Validate the page number
        if (page <= 0) return ResponseEntity.badRequest().body("Page number must be greater than 0");

        // Validate the page size
        if (pageSize <= 0) return ResponseEntity.badRequest().body("Page size must be greater than 0");

        // Validate the radius
        if (radius != null && radius < 0) return ResponseEntity.badRequest().body("Radius must be greater than 0");

        LocationDTO location = null;
        if (longitude != null && latitude != null) {
            location = new LocationDTO();
            location.setLongitude(longitude);
            location.setLatitude(latitude);
        }

        if ((location == null) != (radius == null))
            return ResponseEntity.badRequest().body("Either both location and radius must be specified, or neither");

        // If the user wants to see their favorites, set the favoritesOf field
        User favoritesOf = null;
        if (favorites != null && favorites) {
            favoritesOf = authenticationService.getLoggedInUser();
        }

        if (user != null) {
            Boolean ownerOrAdmin = authenticationService.isAdminOrAllowed(u -> u.getId() == user);
            if (ownerOrAdmin == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            if (!ownerOrAdmin) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // Build the filter
        ListingFilterBuilder filterBuilder = ListingFilter.builder();
        filterBuilder
                .searchString(searchString)
                .locationRadius(location, radius)
                .categories(categories)
                .userId(user)
                .favoritesOf(favoritesOf)
                .states(state);
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
        if (listings.getNumber() < listings.getTotalPages() - 1) {
            nextUrl = requestUrl + "page=" + (page + 1) + "&pageSize=" + pageSize;
        }
        if (listings.getNumber() > 0) {
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

    /**
     * Get a listing by ID
     * @return Listing
     */
    @PostMapping
    public ResponseEntity<Object> createListing(@RequestBody ListingCreationDTO listingCreationDTO) {
        // Validate the input
        if (Stream.of(
                listingCreationDTO.getCategoryId(),
                listingCreationDTO.getSummary(),
                listingCreationDTO.getDescription(),
                listingCreationDTO.getPrice(),
                listingCreationDTO.getTitle(),
                listingCreationDTO.getGalleryIds(),
                listingCreationDTO.getThumbnailIndex()
        ).anyMatch(Objects::isNull)
        ) {
            return ResponseEntity.badRequest().body("All fields must be specified");
        }

        User user = authenticationService.getLoggedInUser();
        if (user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Listing listing = mapper.toListing(listingCreationDTO);
        Listing savedListing;
        try {
            savedListing = listingService.createListing(listing,
                    SecurityContextHolder.getContext().getAuthentication().getName());
        } catch (DataIntegrityViolationException e) {
            logger.info("Invalid ID in gallery, thumbnail, or category for listing creation");
            return ResponseEntity.badRequest().body("Invalid ID in gallery, thumbnail, or category");
        }

        logger.info("Listing created: " + savedListing.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toListingFullDTO(savedListing));
    }

    /**
     * Update a listing
     * @param listingDTO Listing to update
     * @return Updated listing
     */
    @PutMapping
    public ResponseEntity<Object> updateListing(@RequestBody ListingUpdateDTO listingDTO) {
        Listing oldListing = listingService.getListing(listingDTO.getId());
        if (oldListing == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Listing not found");

        Boolean selfOrAdmin = authenticationService.isAdminOrAllowed(user -> user.getId() == oldListing.getSeller().getId());
        if (selfOrAdmin == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if (!selfOrAdmin) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        logger.info("Listing updated: " + listingDTO.getId());
        Listing savedListing = listingService.updateListing(mapper.toListing(listingDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toListingMinimalDTO(savedListing));
    }

    /**
     * Get a listing by ID
     * @param id Listing ID
     * @return Listing
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getListing(@PathVariable long id) {
        Listing listing = listingService.getListing(id);
        if (listing == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Listing not found");
        }

        return ResponseEntity.ok(mapper.toListingFullDTO(listing));
    }

    /**
     * Delete a listing
     * @param id Listing ID
     * @return Status code
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteListing(@PathVariable long id) {
        Listing listing = listingService.getListing(id);
        if (listing == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Listing not found");
        }

        Boolean ownerOrAdmin = authenticationService.isAdminOrAllowed(user -> user.getId() == listing.getSeller().getId());
        if (ownerOrAdmin == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if (!ownerOrAdmin) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        logger.info("Listing deleted: " + id);
        listingService.deleteListing(listing);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Listing deleted");
    }

    /**
     * Check if the listing is favorited by the logged in user
     * @param id Listing ID
     * @return True if favorited, false otherwise
     */
    @GetMapping("/{id}/favorite")
    public ResponseEntity<Object> isListingFavorited(@PathVariable long id) {
        Listing listing = listingService.getListing(id);
        User user = authenticationService.getLoggedInUser();
        ResponseEntity<Object> response = validateListingAccess(listing, user);
        if (response != null) {
            if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) return ResponseEntity.ok(false);
            return response;
        }

        return ResponseEntity.ok(userService.isListingFavorited(user, listing));
    }

    /**
     * Favorite a listing for the logged-in user
     * @param id Listing ID
     * @return Status code
     */
    @PostMapping("/{id}/favorite")
    public ResponseEntity<Object> favoriteListing(@PathVariable long id) {
        Listing listing = listingService.getListing(id);
        User user = authenticationService.getLoggedInUser();
        ResponseEntity<Object> response = validateListingAccess(listing, user);
        if (response != null) return response;

        userService.favoriteListing(user, listing);
        return ResponseEntity.ok("Listing favorited");
    }

    /**
     * Unfavorite a listing for the logged-in user
     * @param id Listing ID
     * @return Status code
     */
    @DeleteMapping("/{id}/favorite")
    public ResponseEntity<Object> unfavoriteListing(@PathVariable long id) {
        Listing listing = listingService.getListing(id);
        User user = authenticationService.getLoggedInUser();
        ResponseEntity<Object> response = validateListingAccess(listing, user);
        if (response != null) return response;

        userService.unfavoriteListing(user, listing);
        return ResponseEntity.ok("Listing unfavorited");
    }

    /**
     * Check if a user has access to a listing. Currently, this is only used to check if a user is logged in.
     * @param listing Listing to check
     * @param user User to check
     * @return Null if the user has access, otherwise a response entity with the error
     */
    private ResponseEntity<Object> validateListingAccess(Listing listing, User user) {
        if (listing == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Listing not found");
        if (user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return null;
    }
}
