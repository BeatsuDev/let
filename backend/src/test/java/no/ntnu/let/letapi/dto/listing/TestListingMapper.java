package no.ntnu.let.letapi.dto.listing;


import no.ntnu.let.letapi.dto.user.UserMapperImpl;
import no.ntnu.let.letapi.model.listing.*;
import no.ntnu.let.letapi.model.user.User;
import no.ntnu.let.letapi.util.DateUtil;
import no.ntnu.let.letapi.util.UrlUtil;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
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
    private final ListingMapper listingMapper = new ListingMapperImpl(new UserMapperImpl());

    @Before
    public void setUp() throws ParseException {
        long id = 1L;
        for (Image image : Stream.of(image1, image2, image3).toList()) {
            image.setId(id);
            image.setFileName("dev-gallery" + id + ".jpg");
        }

        category1.setId(1L);
        category1.setName("Test category 1");

        location1.setId(1L);
        location1.setLatitude(1.0);
        location1.setLongitude(2.0);
        location1.setName("Test location 1");
        locationDTO1 = listingMapper.toLocationDTO(location1);

        user1.setId(1L);
        user1.setFirstName("Test");
        user1.setLastName("User");
        user1.setEmail("user@example.org");
        user1.setAdmin(true);

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
                listingFullDTO.getThumbnail().getUrl());
        assertEquals("Test category 1", listingFullDTO.getCategory().getName());
        assertEquals("Test location 1", listingFullDTO.getLocationName());
        assertEquals(ListingState.ACTIVE, listingFullDTO.getState());
        assertEquals(Stream.of(image1, image2, image3).map(i -> UrlUtil.getImageUrl(i.getFileName())).toList(),
                Arrays.stream(listingFullDTO.getGallery()).map(ImageDTO::getUrl).toList());
        assertEquals("2001-01-08T22:00:00.000Z", listingFullDTO.getCreated());
        assertEquals("Test", listingFullDTO.getSeller().getFirstName());
        assertEquals("User", listingFullDTO.getSeller().getLastName());
        assertEquals("user@example.org", listingFullDTO.getSeller().getEmail());
    }
}
