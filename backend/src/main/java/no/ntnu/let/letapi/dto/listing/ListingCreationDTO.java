package no.ntnu.let.letapi.dto.listing;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListingCreationDTO {
    private String title;
    private String summary;
    private String description;
    private Long price;
    private Long categoryId;
    private LocationDTO location;
    private Long thumbnailId;
    private List<Long> galleryIds;
}
