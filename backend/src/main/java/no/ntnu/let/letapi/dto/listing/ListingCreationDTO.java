package no.ntnu.let.letapi.dto.listing;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * DTO for creating a listing
 */
@Getter
@Setter
public class ListingCreationDTO {
    private String title;
    private String summary;
    private String description;
    private Long price;
    private Long categoryId;
    private LocationDTO location;
    private Integer thumbnailIndex;
    private List<Long> galleryIds;
}
