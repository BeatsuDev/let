package no.ntnu.let.letapi.dto.listing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagedListingsDTO {
    private List<ListingMinimalDTO> listings;
    private int pageNumber;
    private int totalPages;
    private String next;
    private String prev;
}
