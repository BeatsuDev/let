package no.ntnu.let.letapi.dto.listing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import no.ntnu.let.letapi.model.listing.ListingState;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListingDTO {
    private Long id;
    private String title;
    private String shortDescription;
    private String description;
    private Date created;
    private Date sold;
    private int price;
    private LocationDTO location;
    private long sellerId;
    private ImageDTO thumbnail;
    private List<ImageDTO> gallery;
    private ListingState state;
}
