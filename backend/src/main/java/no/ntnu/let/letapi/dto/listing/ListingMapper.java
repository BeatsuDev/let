package no.ntnu.let.letapi.dto.listing;

import no.ntnu.let.letapi.dto.user.UserMapper;
import no.ntnu.let.letapi.model.listing.Category;
import no.ntnu.let.letapi.model.listing.Image;
import no.ntnu.let.letapi.model.listing.Listing;
import no.ntnu.let.letapi.model.listing.Location;
import no.ntnu.let.letapi.util.DateUtil;
import no.ntnu.let.letapi.util.UrlUtil;
import org.mapstruct.*;

import java.util.Date;

@Mapper(componentModel = "spring", uses = {UserMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ListingMapper {
    Location toLocation(LocationDTO locationDTO);
    LocationDTO toLocationDTO(Location location);

    Image toImage(Long id);
    @Named("toImageUrl")
    default String toImageUrl(Image image) {
        if (image == null) return null;
        return UrlUtil.getImageUrl(image.getFileName());
    }
    @Mapping(target = "url", source = "image", qualifiedByName = "toImageUrl")
    ImageDTO toImageDTO(Image image);

    default String formatDate(Date date) {
        return DateUtil.formatDate(date);
    }

    Category toCategory(CategoryCreationDTO categoryCreationDTO);
    CategoryDTO toCategoryDTO(Category category);

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
            @Mapping(target = "thumbnailUrl", source = "thumbnail", qualifiedByName = "toImageUrl"),
            @Mapping(target = "locationName", source = "location.name")
    })
    ListingMinimalDTO toListingMinimalDTO(Listing listing);
    @Mappings({
            @Mapping(target = "categoryName", source = "category.name"),
            @Mapping(target = "thumbnailUrl", source = "thumbnail", qualifiedByName = "toImageUrl"),
            @Mapping(target = "galleryUrls", source = "gallery", qualifiedByName = "toImageUrl"),
            @Mapping(target = "locationName", source = "location.name"),
            @Mapping(target = "seller", source = "seller", qualifiedByName = "toMinimalDTO")
    })
    ListingFullDTO toListingFullDTO(Listing listing);
}
