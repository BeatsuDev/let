package no.ntnu.let.letapi.dto.listing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import no.ntnu.let.letapi.util.UrlUtil;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageDTO {
    private static final String BASE_URL = UrlUtil.getBaseUrl() + "/image/";
    private long id;

    public String getUrl() {
        return BASE_URL + id;
    }
}
