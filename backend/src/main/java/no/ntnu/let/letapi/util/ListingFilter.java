package no.ntnu.let.letapi.util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import no.ntnu.let.letapi.dto.listing.LocationDTO;
import no.ntnu.let.letapi.model.listing.ListingState;

import java.util.List;

@Getter
@Setter(AccessLevel.PROTECTED)
public class ListingFilter {
    private String searchString = null;
    private LocationDTO location = null;
    private Integer radius = null;
    private List<Integer> categories = null;
    private Long userId = null;
    private Boolean favorites = null;
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

        if (favorites != null) {
            sb.append("favorites=");
            sb.append(favorites);
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
}
