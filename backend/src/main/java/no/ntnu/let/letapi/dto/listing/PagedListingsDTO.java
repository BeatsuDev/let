package no.ntnu.let.letapi.dto.listing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
