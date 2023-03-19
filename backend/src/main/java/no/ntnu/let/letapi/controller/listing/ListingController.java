package no.ntnu.let.letapi.controller.listing;

import no.ntnu.let.letapi.dto.listing.ListingDTO;
import no.ntnu.let.letapi.dto.listing.LocationDTO;
import no.ntnu.let.letapi.model.listing.ListingState;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/listing")
public class ListingController {
    @GetMapping
    public ResponseEntity<Object> getListings(
            @RequestParam String searchString,
            @RequestParam LocationDTO locationDTO,
            @RequestParam int radius,
            @RequestParam List<Integer> categories,
            @RequestParam boolean favorites,
            @RequestParam List<ListingState> states,
            @RequestParam int limit,
            @RequestParam int offset
            ) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PostMapping
    public ResponseEntity<Object> createListing(@RequestBody ListingDTO listingDTO) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PutMapping
    public ResponseEntity<Object> updateListing(@RequestBody ListingDTO listingDTO) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getListing(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteListing(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}
