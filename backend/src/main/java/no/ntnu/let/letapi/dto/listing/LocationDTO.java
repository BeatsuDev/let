package no.ntnu.let.letapi.dto.listing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {
    private long id;
    private float latitude;
    private float longitude;
    private String name;
}
