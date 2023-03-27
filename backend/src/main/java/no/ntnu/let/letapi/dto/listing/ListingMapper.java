package no.ntnu.let.letapi.dto.listing;

import no.ntnu.let.letapi.dto.user.UserMapper;
import no.ntnu.let.letapi.model.listing.Category;
import no.ntnu.let.letapi.model.listing.Image;
import no.ntnu.let.letapi.model.listing.Listing;
import no.ntnu.let.letapi.model.listing.Location;
import no.ntnu.let.letapi.repository.listing.CategoryRepository;
import no.ntnu.let.letapi.repository.listing.ImageRepository;
import no.ntnu.let.letapi.util.DateUtil;
import no.ntnu.let.letapi.util.UrlUtil;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Mapper for listings
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class ListingMapper {
    @Autowired
    protected CategoryRepository categoryRepository;
    @Autowired
    protected ImageRepository imageRepository;

    /**
     * Map p location DTO to a location
     * @param locationDTO The location DTO
     * @return The location entity
     */
    public abstract Location toLocation(LocationDTO locationDTO);

    /**
     * Map a location to a listing DTO
     * @param location The location
     * @return The location DTO
     */
    public abstract LocationDTO toLocationDTO(Location location);

    /**
     * Map an image to an image URL
     * @param image The image
     * @return The image URL
     */
    @Named("toImageUrl")
    public String toImageUrl(Image image) {
        if (image == null) return null;
        return UrlUtil.getImageUrl(image.getFileName());
    }
    /**
     * Map an image to an image DTO
     * @param image The image
     * @return The image DTO
     */
    @Mapping(target = "url", source = "image", qualifiedByName = "toImageUrl")
    public abstract ImageDTO toImageDTO(Image image);
    /**
     * Map an image ID to an image reference
     * @param id The image ID
     * @return The image reference
     */
    public Image toImageReference(Long id) {
        if (id == null) return null;
        return imageRepository.getReferenceById(id);
    }
    /**
     * Map a list of image IDs and an index to an image reference
     * @param gallery The list of image IDs
     * @param index The index of the image to get
     */
    public Image toImageReference(List<Long> gallery, Integer index) {
        if (gallery == null || index == null) return null;
        return toImageReference(gallery.get(index));
    }

    /**
     * Map a date to a formatted date string
     * @param date The date
     * @return The formatted date string
     */
    String formatDate(Date date) {
        return DateUtil.formatDate(date);
    }

    /**
     * Map a category creation DTO to a category
     * @param categoryCreationDTO The category creation DTO
     * @return The category
     */
    public abstract Category toCategory(CategoryCreationDTO categoryCreationDTO);

    /**
     * Map a category to a category DTO
     * @param category The category
     * @return The category DTO
     */
    public abstract CategoryDTO toCategoryDTO(Category category);

    /**
     * Map a category ID to a category reference
     * @param id The category ID
     * @return The category reference
     */
    public Category toCategoryReference(Long id) {
        if (id == null) return null;
        return categoryRepository.getReferenceById(id);
    }

    /**
     * Map a listing creation DTO to a listing
     * @param listingCreationDTO The listing creation DTO
     * @return The listing
     */
    @Mappings({
            @Mapping(target = "category", source = "categoryId"),
            @Mapping(target = "thumbnail", expression = "java(toImageReference(listingCreationDTO.getGalleryIds(), " +
                    "listingCreationDTO.getThumbnailIndex()))"),
            @Mapping(target = "gallery", source = "galleryIds"),
    })
    public abstract Listing toListing(ListingCreationDTO listingCreationDTO);

    /**
     * Map a listing update DTO to a listing
     * @param listingUpdateDTO The listing update DTO
     * @return The listing
     */
    @Mappings({
            @Mapping(target = "category", source = "categoryId"),
            @Mapping(target = "thumbnail", expression = "java(toImageReference(listingUpdateDTO.getGalleryIds(), " +
                    "listingUpdateDTO.getThumbnailIndex()))"),
            @Mapping(target = "gallery", source = "galleryIds"),
    })
    public abstract Listing toListing(ListingUpdateDTO listingUpdateDTO);

    /**
     * Map a listing to a listing minimal DTO
     * @param listing The listing
     * @return The listing minimal DTO
     */
    @Named("toMinimalListingDTO")
    @Mappings({
            @Mapping(target = "thumbnailUrl", source = "thumbnail", qualifiedByName = "toImageUrl"),
            @Mapping(target = "locationName", source = "location.name"),
            @Mapping(target = "categoryName", source = "category.name"),
    })
    public abstract ListingMinimalDTO toListingMinimalDTO(Listing listing);
    /**
     * Map a listing to a listing full DTO
     * @param listing The listing
     * @return The listing full DTO
     */
    @Mappings({
            @Mapping(target = "locationName", source = "location.name"),
            @Mapping(target = "gallery", source = "gallery"),
            @Mapping(target = "seller", source = "seller", qualifiedByName = "toMinimalDTO")
    })
    public abstract ListingFullDTO toListingFullDTO(Listing listing);
}
