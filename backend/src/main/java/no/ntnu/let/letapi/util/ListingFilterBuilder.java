package no.ntnu.let.letapi.util;

import no.ntnu.let.letapi.dto.listing.LocationDTO;
import no.ntnu.let.letapi.model.listing.ListingState;
import no.ntnu.let.letapi.model.user.User;

import java.util.List;

public class ListingFilterBuilder {
    private final ListingFilter filter = new ListingFilter();

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
