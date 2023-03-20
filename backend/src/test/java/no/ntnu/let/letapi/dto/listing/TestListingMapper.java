package no.ntnu.let.letapi.dto.listing;


import no.ntnu.let.letapi.dto.user.UserMapperImpl;
import no.ntnu.let.letapi.model.listing.*;
import no.ntnu.let.letapi.model.user.User;
import no.ntnu.let.letapi.util.DateUtil;
import no.ntnu.let.letapi.util.UrlUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestListingMapper {
    private final Image image1 = new Image();
    private final Image image2 = new Image();
    private final Image image3 = new Image();
    private final Category category1 = new Category();
    private final Location location1 = new Location();
    private final User user1 = new User();
    private LocationDTO locationDTO1 = new LocationDTO();
    private final Listing listing1 = new Listing();
    private final ListingUpdateDTO listingUpdateDTO1 = new ListingUpdateDTO();
    private final ListingMapper listingMapper = new ListingMapperImpl(new UserMapperImpl());

    @BeforeEach
    public void resetImages() {
        long id = 1L;
        for (Image image : Stream.of(image1, image2, image3).toList()) {
            image.setId(id);
            image.setFileName("dev-gallery" + id + ".jpg");
        }
    }

    @BeforeEach
    public void resetCategories() {
        category1.setId(1L);
        category1.setName("Test category 1");
    }

    @BeforeEach
    public void resetLocations() {
        location1.setId(1L);
        location1.setLatitude(1.0);
        location1.setLongitude(2.0);
        location1.setName("Test location 1");
        locationDTO1 = listingMapper.toLocationDTO(location1);
    }

    @BeforeEach
    public void resetUsers() {
        user1.setId(1L);
        user1.setFirstName("Test");
        user1.setLastName("User");
        user1.setEmail("user@example.org");
        user1.setAdmin(true);
    }

    @BeforeEach
    public void resetListings() throws ParseException {
        listing1.setId(1L);
        listing1.setTitle("Test listing 1");
        listing1.setSummary("Test short description 1");
        listing1.setDescription("Test description 1");
        listing1.setPrice(100L);
        listing1.setThumbnail(image1);
        listing1.setGallery(List.of(
                image1,
                image2,
                image3
        ));
        listing1.setCategory(category1);
        listing1.setLocation(location1);
        listing1.setState(ListingState.ACTIVE);
        listing1.setCreated(DateUtil.parseDate("2001-01-08T22:00:00.000Z"));
        listing1.setSeller(user1);

        listingUpdateDTO1.setId(1L);
        listingUpdateDTO1.setTitle("Test listing 1");
        listingUpdateDTO1.setSummary("Test short description 1");
        listingUpdateDTO1.setDescription("Test description 1");
        listingUpdateDTO1.setPrice(100L);
        listingUpdateDTO1.setThumbnailId(1L);
        listingUpdateDTO1.setGalleryIds(List.of(
                1L,
                2L,
                3L
        ));
        listingUpdateDTO1.setCategoryId(1L);
        listingUpdateDTO1.setLocation(locationDTO1);
        listingUpdateDTO1.setState(ListingState.ACTIVE);
    }

    @Test
    public void testToLocation() {
        Location location = listingMapper.toLocation(locationDTO1);
        assertEquals(1.0, location.getLatitude());
        assertEquals(2.0, location.getLongitude());
        assertEquals("Test location 1", location.getName());
    }

    @Test
    public void testToLocationDto() {
        LocationDTO locationDTO = listingMapper.toLocationDTO(location1);
        assertEquals(1.0, locationDTO.getLatitude());
        assertEquals(2.0, locationDTO.getLongitude());
        assertEquals("Test location 1", locationDTO.getName());
    }

    @Test
    public void testToImage() {
        Image image = listingMapper.toImage(1L);
        assertEquals(1L, image.getId());
    }

    @Test
    public void testToImageDTO() {
        ImageDTO imageDTO = listingMapper.toImageDTO(image1);
        assertEquals(1L, imageDTO.getId());
        assertEquals(UrlUtil.getImageUrl(image1.getFileName()), imageDTO.getUrl());
    }

    @Test
    public void testToCategory() {
        CategoryCreationDTO listingDTO= new CategoryCreationDTO();
        listingDTO.setName("Test category 1");

        Category category = listingMapper.toCategory(listingDTO);
        assertEquals("Test category 1", category.getName());
    }

    @Test
    public void testToCategoryDTO() {
        CategoryDTO categoryDTO = listingMapper.toCategoryDTO(category1);
        assertEquals(1L, categoryDTO.getId());
        assertEquals("Test category 1", categoryDTO.getName());
    }

    @Test void testToListingFromListingCreateDTL() {
        Listing listing = listingMapper.toListing((ListingCreationDTO) listingUpdateDTO1);

        assertEquals("Test listing 1", listing.getTitle());
        assertEquals("Test short description 1", listing.getSummary());
        assertEquals("Test description 1", listing.getDescription());
        assertEquals(100L, listing.getPrice());
        assertEquals(1L, listing.getThumbnail().getId());
        assertEquals(3, listing.getGallery().size());
        assertEquals(1L, listing.getCategory().getId());
        assertEquals(1.0, listing.getLocation().getLatitude());
        assertEquals(2.0, listing.getLocation().getLongitude());
        assertEquals("Test location 1", listing.getLocation().getName());
    }

    @Test
    public void testToListingFromListingUpdateDTO() {
        Listing listing = listingMapper.toListing(listingUpdateDTO1);

        assertEquals(1L, listing.getId());
        assertEquals("Test listing 1", listing.getTitle());
        assertEquals("Test short description 1", listing.getSummary());
        assertEquals("Test description 1", listing.getDescription());
        assertEquals(100L, listing.getPrice());
        assertEquals(1L, listing.getThumbnail().getId());
        assertEquals(3, listing.getGallery().size());
        assertEquals(1L, listing.getCategory().getId());
        assertEquals(1.0, listing.getLocation().getLatitude());
        assertEquals(2.0, listing.getLocation().getLongitude());
        assertEquals("Test location 1", listing.getLocation().getName());
    }

    @Test
    public void testToListingMinimalDto() {
        ListingMinimalDTO listingMinimalDTO = listingMapper.toListingMinimalDTO(listing1);

        assertEquals(1L, listingMinimalDTO.getId());
        assertEquals("Test listing 1", listingMinimalDTO.getTitle());
        assertEquals("Test short description 1", listingMinimalDTO.getSummary());
        assertEquals(100L, listingMinimalDTO.getPrice());
        assertEquals(UrlUtil.getImageUrl(image1.getFileName()),
                listingMinimalDTO.getThumbnailUrl());
        assertEquals("Test category 1", listingMinimalDTO.getCategoryName());
        assertEquals("Test location 1", listingMinimalDTO.getLocationName());
    }

    @Test
    public void testToListingFullDto() {
        ListingFullDTO listingFullDTO = listingMapper.toListingFullDTO(listing1);

        assertEquals(1L, listingFullDTO.getId());
        assertEquals("Test listing 1", listingFullDTO.getTitle());
        assertEquals("Test short description 1", listingFullDTO.getSummary());
        assertEquals("Test description 1", listingFullDTO.getDescription());
        assertEquals(100L, listingFullDTO.getPrice());
        assertEquals(UrlUtil.getImageUrl(image1.getFileName()),
                listingFullDTO.getThumbnailUrl());
        assertEquals("Test category 1", listingFullDTO.getCategoryName());
        assertEquals("Test location 1", listingFullDTO.getLocationName());
        assertEquals(ListingState.ACTIVE, listingFullDTO.getState());
        assertEquals(Stream.of(image1, image2, image3)
                        .map(i -> UrlUtil.getImageUrl(i.getFileName())).toList(),
                List.of(listingFullDTO.getGalleryUrls()));
        assertEquals("2001-01-08T22:00:00.000Z", listingFullDTO.getCreated());
        assertEquals("Test", listingFullDTO.getSeller().getFirstName());
        assertEquals("User", listingFullDTO.getSeller().getLastName());
        assertEquals("user@example.org", listingFullDTO.getSeller().getEmail());
    }
}
