package no.ntnu.let.letapi.dto.listing;

import lombok.Getter;
import lombok.Setter;
import no.ntnu.let.letapi.model.listing.ListingState;

import java.util.Date;

@Getter
@Setter
public class ListingFullDTO extends ListingMinimalDTO {
    private String description;
    private ListingState state;
    private String created;
    private String[] galleryUrls;
}
