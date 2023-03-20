package no.ntnu.let.letapi;

import no.ntnu.let.letapi.model.listing.*;
import no.ntnu.let.letapi.model.user.User;
import no.ntnu.let.letapi.repository.listing.CategoryRepository;
import no.ntnu.let.letapi.repository.listing.ImageRepository;
import no.ntnu.let.letapi.repository.listing.ListingRepository;
import no.ntnu.let.letapi.repository.listing.LocationRepository;
import no.ntnu.let.letapi.repository.user.UserRepository;
import no.ntnu.let.letapi.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

/**
 * Main class for the LetAPI application.
 */
@SpringBootApplication
public class LetAPI {
    @Autowired private ImageRepository imageRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private LocationRepository locationRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private ListingRepository listingRepository;
    @Autowired private PasswordEncoder passwordEncoder;


    public static void main(String[] args) {
        SpringApplication.run(LetAPI.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void addMockData() {
        User[] users = new User[]{
                new User(
                        1L,
                        "user@example.org",
                        "Example",
                        "User",
                        passwordEncoder.encode("user"),
                        false
                ),
                new User(
                        2L,
                        "admin@example.org",
                        "Example",
                        "Admin",
                        passwordEncoder.encode("admin"),
                        true
                )
        };
        userRepository.saveAll(Arrays.asList(users));

        Image[] images = new Image[] {
            new Image(1, "dev-gallery1.jpg"),
            new Image(2, "dev-gallery2.jpg"),
            new Image(3, "dev-gallery3.jpg"),
            new Image(4, "dev-gallery4.jpg"),
            new Image(5, "dev-gallery5.jpg"),
            new Image(6, "dev-gallery6.jpg"),
            new Image(7, "dev-thumbnail1.jpg"),
        };
        imageRepository.saveAll(Arrays.asList(images));

        Category[] categories = new Category[] {
            new Category(1, "Furniture"),
            new Category(2, "Electronics"),
            new Category(3, "Clothes"),
            new Category(4, "Books"),
            new Category(5, "Other"),
        };
        categoryRepository.saveAll(Arrays.asList(categories));

        Location[] locations = new Location[] {
                new Location(1L, 59.6633005, 10.6293392, "Drøbak, Norge"),
                new Location(2L, 59.9479953, 10.9276201, "Høybråten, Norge")
        };
        locationRepository.saveAll(Arrays.asList(locations));

        Listing[] listings = new Listing[]{
                new Listing(
                        1L,
                        "Sofa",
                        "A nice sofa",
                        "A nice sofa, in good condition", DateUtil.getNow(),
                        null,
                        1000,
                        categories[0],
                        locations[0],
                        users[0],
                        images[6],
                        List.of(images),
                        ListingState.ACTIVE
                ),
                new Listing(
                        2L,
                        "TV",
                        "A nice TV",
                        "A nice TV, in good condition", DateUtil.getNow(),
                        null,
                        2000,
                        categories[1],
                        locations[1],
                        users[1],
                        images[6],
                        List.of(images),
                        ListingState.ACTIVE
                ),
        };
        listingRepository.saveAll(Arrays.asList(listings));
    }
}
