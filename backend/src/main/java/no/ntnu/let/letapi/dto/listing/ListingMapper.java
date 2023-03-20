package no.ntnu.let.letapi.dto.listing;

import no.ntnu.let.letapi.model.listing.Category;
import no.ntnu.let.letapi.model.listing.Image;
import no.ntnu.let.letapi.model.listing.Listing;
import no.ntnu.let.letapi.model.listing.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ListingMapper {
    Location toLocation(LocationDTO locationDTO);
    LocationDTO toLocationDTO(Location location);

    Image toImage(Long id);
    ImageDTO toImageDTO(Image image);
    default String toImageUrl(Image image) {
        ImageDTO imageDTO = toImageDTO(image);
        return imageDTO.getUrl();
    }

    String toCategoryName(Long id);
    Category toCategory(Long id);

    @Mappings({
            @Mapping(target = "thumbnail", source = "thumbnailId"),
            @Mapping(target = "gallery", source = "galleryIds")
    })
    Listing toListing(ListingCreationDTO listingCreationDTO);
    @Mappings({
            @Mapping(target = "thumbnail", source = "thumbnailId"),
            @Mapping(target = "gallery", source = "galleryIds")
    })
    Listing toListing(ListingUpdateDTO listingUpdateDTO);
    @Mappings({
            @Mapping(target = "thumbnailUrl", source = "thumbnail.url"),
            @Mapping(target = "locationName", source = "location.name")
    })
    ListingMinimalDTO toListingMinimalDTO(Listing listing);
    @Mappings({
            @Mapping(target = "thumbnailUrl", source = "thumbnail"),
            @Mapping(target = "galleryUrls", source = "gallery"),
            @Mapping(target = "locationName", source = "location.name")
    })
    ListingFullDTO toListingFullDTO(Listing listing);
}