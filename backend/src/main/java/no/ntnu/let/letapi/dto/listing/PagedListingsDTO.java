package no.ntnu.let.letapi.dto.listing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * DTO for a paged list of listings
 */
@Getter
@Setter
@AllArgsConstructor
public class PagedListingsDTO {
    private List<ListingMinimalDTO> listings;
    private int pageNumber;
    private int totalPages;
    private String next;
    private String prev;
}
