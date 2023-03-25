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

@Mapper(componentModel = "spring", uses = {UserMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class ListingMapper {
    @Autowired
    protected CategoryRepository categoryRepository;
    @Autowired
    protected ImageRepository imageRepository;

    public abstract Location toLocation(LocationDTO locationDTO);
    public abstract LocationDTO toLocationDTO(Location location);

    @Named("toImageUrl")
    public String toImageUrl(Image image) {
        if (image == null) return null;
        return UrlUtil.getImageUrl(image.getFileName());
    }
    @Mapping(target = "url", source = "image", qualifiedByName = "toImageUrl")
    public abstract ImageDTO toImageDTO(Image image);

    String formatDate(Date date) {
        return DateUtil.formatDate(date);
    }

    public abstract Category toCategory(CategoryCreationDTO categoryCreationDTO);
    public abstract CategoryDTO toCategoryDTO(Category category);

    @Mappings({
            @Mapping(target = "category", expression = "java(categoryRepository.getReferenceById" +
                    "(listingCreationDTO.getCategoryId()))"),
            @Mapping(target = "thumbnail", expression = "java(imageRepository.getReferenceById" +
                    "(listingCreationDTO.getThumbnailId()))"),
            @Mapping(target = "gallery",
                    expression = "java(listingCreationDTO.getGalleryIds().stream().map" +
                            "(imageRepository::getReferenceById).toList())"),
    })
    public abstract Listing toListing(ListingCreationDTO listingCreationDTO);
    @Mappings({
            @Mapping(target = "category", expression = "java(categoryRepository.getReferenceById" +
                    "(listingUpdateDTO.getCategoryId()))"),
            @Mapping(target = "thumbnail", expression = "java(imageRepository.getReferenceById" +
                    "(listingUpdateDTO.getThumbnailId()))"),
            @Mapping(target = "gallery",
                    expression = "java(listingUpdateDTO.getGalleryIds().stream().map" +
                            "(imageRepository::getReferenceById).toList())"),
    })
    public abstract Listing toListing(ListingUpdateDTO listingUpdateDTO);
    @Named("toMinimalListingDTO")
    @Mappings({
            @Mapping(target = "categoryName", source = "category.name"),
            @Mapping(target = "thumbnailUrl", source = "thumbnail", qualifiedByName = "toImageUrl"),
            @Mapping(target = "locationName", source = "location.name")
    })
    public abstract ListingMinimalDTO toListingMinimalDTO(Listing listing);
    @Mappings({
            @Mapping(target = "categoryName", source = "category.name"),
            @Mapping(target = "thumbnailUrl", source = "thumbnail", qualifiedByName = "toImageUrl"),
            @Mapping(target = "galleryUrls", source = "gallery", qualifiedByName = "toImageUrl"),
            @Mapping(target = "locationName", source = "location.name"),
            @Mapping(target = "seller", source = "seller", qualifiedByName = "toMinimalDTO")
    })
    public abstract ListingFullDTO toListingFullDTO(Listing listing);
}
