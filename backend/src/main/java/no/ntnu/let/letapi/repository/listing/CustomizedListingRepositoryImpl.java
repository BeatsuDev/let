package no.ntnu.let.letapi.repository.listing;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import no.ntnu.let.letapi.dto.listing.LocationDTO;
import no.ntnu.let.letapi.model.listing.Listing;
import no.ntnu.let.letapi.model.user.User;
import no.ntnu.let.letapi.repository.user.UserRepository;
import no.ntnu.let.letapi.util.ListingFilter;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.geo.Point;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

public class CustomizedListingRepositoryImpl implements CustomizedListingRepository {
    private final EntityManager entityManager;
    private final ListingRepository listingRepository;
    private final UserRepository userRepository;

    @Lazy
    protected CustomizedListingRepositoryImpl(EntityManager entityManager, ListingRepository listingRepository,
                                              UserRepository userRepository) {
        this.entityManager = entityManager;
        this.listingRepository = listingRepository;
        this.userRepository = userRepository;
    }

    private <T> Predicate searchLowerCase(CriteriaBuilder cb, Root<T> root, String field, String value) {
        return cb.like(cb.lower(root.get(field)), "%" + value.toLowerCase() + "%");
    }

    private Double distanceBetweenLocations(double lat1, double lat2, double lon1, double lon2) {
        final int R = 6371; // Radius of the earth

        // Round to 3 decimals for privacy reasons
        lat1 = Math.round(lat1 * 1000.0) / 1000.0;
        lat2 = Math.round(lat2 * 1000.0) / 1000.0;
        lon1 = Math.round(lon1 * 1000.0) / 1000.0;
        lon2 = Math.round(lon2 * 1000.0) / 1000.0;

        // Haversine formula
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }

    public Page<Listing> findAll(ListingFilter filter, PageRequest pageRequest) {
        if (filter == null) return listingRepository.findAll(pageRequest);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Listing> cq = cb.createQuery(Listing.class);
        Root<Listing> listing = cq.from(Listing.class);

        Predicate searchString;
        if (filter.getSearchString() != null) {
            Predicate titleSearchString = searchLowerCase(cb, listing, "title", filter.getSearchString());
            Predicate summarySearchString = searchLowerCase(cb, listing, "summary", filter.getSearchString());
            Predicate descriptionSearchString = searchLowerCase(cb, listing, "description", filter.getSearchString());

            searchString = cb.or(titleSearchString, summarySearchString, descriptionSearchString);
        } else {
            searchString = cb.conjunction();
        }

        Predicate categoryFilter;
        if (filter.getCategories() != null) {
            categoryFilter = cb.in(listing.get("category")).value(filter.getCategories());
        } else {
            categoryFilter = cb.conjunction();
        }

        Predicate userIdFilter;
        if (filter.getUserId() != null) {
            userIdFilter = cb.equal(listing.get("seller"), filter.getUserId());
        } else {
            userIdFilter = cb.conjunction();
        }

        Predicate favoritesFilter;
        if (filter.getFavoritesOf() != null) {
            User user = userRepository.findById(filter.getFavoritesOf().getId()).orElse(null);
            if (user == null) {
                favoritesFilter = cb.disjunction();
            } else {
                favoritesFilter = cb.in(listing.get("id")).value(user.getFavorites().stream().map(Listing::getId).toList());
            }
        } else {
            favoritesFilter = cb.conjunction();
        }

        Predicate stateFilter;
        if (filter.getStates() != null) {
            stateFilter = cb.in(listing.get("state")).value(filter.getStates());
        } else {
            stateFilter = cb.conjunction();
        }

        Predicate finalFilter = cb.and(searchString, categoryFilter, userIdFilter, favoritesFilter, stateFilter);
        List<Listing> listings = entityManager.createQuery(cq.select(listing).where(finalFilter)).getResultList();

        if (filter.getLocation() != null) {
            LocationDTO location = filter.getLocation();
            int radius = filter.getRadius();


            int earthRadius = 6371;
            listings = listings.stream().filter(l -> {
                double distance = distanceBetweenLocations(location.getLatitude(), l.getLocation().getLatitude(),
                        location.getLongitude(), l.getLocation().getLongitude());
                return distance <= radius;
            }).toList();
        }

        long countResult = listings.size();
        listings = listings.stream().skip(pageRequest.getOffset()).limit(pageRequest.getPageSize()).toList();
        return PageableExecutionUtils.getPage(listings, pageRequest, () -> countResult);
    }
}
