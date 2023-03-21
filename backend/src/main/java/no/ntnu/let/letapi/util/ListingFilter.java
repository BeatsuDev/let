package no.ntnu.let.letapi.util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import no.ntnu.let.letapi.dto.listing.LocationDTO;
import no.ntnu.let.letapi.model.listing.ListingState;
import no.ntnu.let.letapi.model.user.User;

import java.util.List;

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
    public static class ListingFilterBuilder {
        private final ListingFilter filter = new ListingFilter();

        private ListingFilterBuilder() {
        }

        public ListingFilterBuilder searchString(String searchString) {
            filter.setSearchString(searchString);
            return this;
        }

        public ListingFilterBuilder locationRadius(LocationDTO location, Integer radius) {
            if ((location == null) != (radius == null)) {
                throw new IllegalArgumentException("Location and radius must be both null or both not null");
            }

            filter.setLocation(location);
            filter.setRadius(radius);
            return this;
        }

        public ListingFilterBuilder categories(List<Integer> categories) {
            filter.setCategories(categories);
            return this;
        }

        public ListingFilterBuilder userId(Long userId) {
            filter.setUserId(userId);
            return this;
        }

        public ListingFilterBuilder favoritesOf(User user) {
            filter.setFavoritesOf(user);
            return this;
        }

        public ListingFilterBuilder states(List<ListingState> states) {
            filter.setStates(states);
            return this;
        }

        public ListingFilter build() {
            return filter;
        }
    }

    public static ListingFilterBuilder builder() {
        return new ListingFilterBuilder();
    }
}
