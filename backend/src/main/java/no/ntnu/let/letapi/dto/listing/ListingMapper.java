package no.ntnu.let.letapi.dto.listing;

import no.ntnu.let.letapi.model.listing.Category;
import no.ntnu.let.letapi.model.listing.Image;
import no.ntnu.let.letapi.model.listing.Listing;
import no.ntnu.let.letapi.model.listing.Location;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ListingMapper {
    Location toLocation(LocationDTO locationDTO);
    LocationDTO toLocationDTO(Location location);
    String toLocationName(Long id);

    Image toImage(Long id);
    ImageDTO toImageDTO(Image image);
    String toImageUrl(Long id);

    String toCategoryName(Long id);
    Category toCategory(Long id);

    Listing toListing(ListingCreationDTO listingCreationDTO);
    Listing toListing(ListingUpdateDTO listingUpdateDTO);
    ListingMinimalDTO toListingMinimalDTO(Listing listing);
    ListingFullDTO toListingFullDTO(Listing listing);
}
