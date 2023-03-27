package no.ntnu.let.letapi.dto.listing;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO for a location
 */
@Getter
@Setter
public class LocationDTO {
    private double latitude;
    private double longitude;
    private String name;
}
