package no.ntnu.let.letapi.model.listing;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import no.ntnu.let.letapi.util.UrlUtil;

@Getter
@Setter
@Entity
public class Image {
    private static final String IMAGE_BASE_URL = UrlUtil.getBaseUrl() + "/image/";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public String getUrl() {
        return UrlUtil.getBaseUrl() + '/' + id;
    }
}
