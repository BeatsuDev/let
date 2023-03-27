package no.ntnu.let.letapi.dto.listing;

import lombok.Getter;
import lombok.Setter;
import no.ntnu.let.letapi.model.listing.ListingState;

/**
 * DTO for updating a listing
 */
@Getter
@Setter
public class ListingUpdateDTO extends ListingCreationDTO {
    private Long id;
    private ListingState state;
}
