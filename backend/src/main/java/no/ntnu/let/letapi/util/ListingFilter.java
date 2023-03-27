package no.ntnu.let.letapi.util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import no.ntnu.let.letapi.dto.listing.LocationDTO;
import no.ntnu.let.letapi.model.listing.ListingState;
import no.ntnu.let.letapi.model.user.User;

import java.util.List;

/**
 * Filter for listings
 */
@Getter
@Setter(AccessLevel.PROTECTED)
public class ListingFilter {
    private String searchString = null;
    private LocationDTO location = null;
    private Integer radius = null;
    private List<Integer> categories = null;
    private Long userId = null;
    private User favoritesOf = null;
    private List<ListingState> states = null;

    /**
     * Convert the filter to a url parameter string
     * @return The url parameter string
     */
    public String toUrlParameters() {
        var sb = new StringBuilder();
        sb.append('?');

        if (searchString != null) {
            sb.append("searchString=");
            sb.append(searchString);
            sb.append('&');
        }

        if (location != null) {
            sb.append("latitude=");
            sb.append(location.getLatitude());
            sb.append("&longitude=");
            sb.append(location.getLongitude());
            sb.append('&');
        }

        if (radius != null) {
            sb.append("radius=");
            sb.append(radius);
            sb.append('&');
        }

        if (categories != null) {
            for (var category : categories) {
                sb.append("categories=");
                sb.append(category);
                sb.append('&');
            }
        }

        if (userId != null) {
            sb.append("userId=");
            sb.append(userId);
            sb.append('&');
        }

        if (favoritesOf != null) {
            sb.append("favorites=true");
            sb.append('&');
        }

        if (states != null) {
            for (var state : states) {
                sb.append("state=");
                sb.append(state);
                sb.append('&');
            }
        }

        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * Builder for listing filters
     */
    public static class ListingFilterBuilder {
        private final ListingFilter filter = new ListingFilter();

        private ListingFilterBuilder() {
        }

        /**
         * Set the search string
         * @param searchString The search string
         * @return The builder
         */
        public ListingFilterBuilder searchString(String searchString) {
            filter.setSearchString(searchString);
            return this;
        }

        /**
         * Set the location and radius
         * @param location The location
         * @param radius The radius
         * @return The builder
         */
        public ListingFilterBuilder locationRadius(LocationDTO location, Integer radius) {
            if ((location == null) != (radius == null)) {
                throw new IllegalArgumentException("Location and radius must be both null or both not null");
            }

            filter.setLocation(location);
            filter.setRadius(radius);
            return this;
        }

        /**
         * Set the categories
         * @param categories The categories
         * @return The builder
         */
        public ListingFilterBuilder categories(List<Integer> categories) {
            filter.setCategories(categories);
            return this;
        }

        /**
         * Set the user id
         * @param userId The user id
         * @return The builder
         */
        public ListingFilterBuilder userId(Long userId) {
            filter.setUserId(userId);
            return this;
        }

        /**
         * Set the user to fetch the favorites of
         * @param user The user
         * @return The builder
         */
        public ListingFilterBuilder favoritesOf(User user) {
            filter.setFavoritesOf(user);
            return this;
        }

        /**
         * Set the states
         * @param states The states
         * @return The builder
         */
        public ListingFilterBuilder states(List<ListingState> states) {
            filter.setStates(states);
            return this;
        }

        /**
         * Build the filter
         * @return The filter
         */
        public ListingFilter build() {
            return filter;
        }
    }

    /**
     * Create a new builder
     * @return The builder
     */
    public static ListingFilterBuilder builder() {
        return new ListingFilterBuilder();
    }
}
