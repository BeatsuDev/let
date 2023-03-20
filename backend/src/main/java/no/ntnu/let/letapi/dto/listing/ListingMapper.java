package no.ntnu.let.letapi.dto.listing;

import no.ntnu.let.letapi.dto.user.UserMapper;
import no.ntnu.let.letapi.model.listing.Image;
import no.ntnu.let.letapi.model.listing.Listing;
import no.ntnu.let.letapi.model.listing.Location;
import no.ntnu.let.letapi.util.DateUtil;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.Date;

@Mapper(componentModel = "spring", uses = {UserMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ListingMapper {
    Location toLocation(LocationDTO locationDTO);
    LocationDTO toLocationDTO(Location location);

    Image toImage(Long id);
    default String toImageUrl(Image image) {
        if (image == null) return null;
        return image.getUrl();
    }

    default String formatDate(Date date) {
        return DateUtil.formatDate(date);
    }

    @Mappings({
            @Mapping(target = "category.id", source = "categoryId"),
            @Mapping(target = "thumbnail", source = "thumbnailId"),
            @Mapping(target = "gallery", source = "galleryIds")
    })
    Listing toListing(ListingCreationDTO listingCreationDTO);
    @Mappings({
            @Mapping(target = "category.id", source = "categoryId"),
            @Mapping(target = "thumbnail", source = "thumbnailId"),
            @Mapping(target = "gallery", source = "galleryIds")
    })
    Listing toListing(ListingUpdateDTO listingUpdateDTO);
    @Mappings({
            @Mapping(target = "categoryName", source = "category.name"),
            @Mapping(target = "thumbnailUrl", source = "thumbnail.url"),
            @Mapping(target = "locationName", source = "location.name")
    })
    ListingMinimalDTO toListingMinimalDTO(Listing listing);
    @Mappings({
            @Mapping(target = "categoryName", source = "category.name"),
            @Mapping(target = "thumbnailUrl", source = "thumbnail"),
            @Mapping(target = "galleryUrls", source = "gallery"),
            @Mapping(target = "locationName", source = "location.name"),
            @Mapping(target = "seller", source = "seller", qualifiedByName = "toMinimalDTO")
    })
    ListingFullDTO toListingFullDTO(Listing listing);
}
