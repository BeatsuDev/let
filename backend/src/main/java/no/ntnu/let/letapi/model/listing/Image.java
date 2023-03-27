package no.ntnu.let.letapi.model.listing;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import no.ntnu.let.letapi.util.UrlUtil;

/**
 * Model for an image
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    private static final String IMAGE_BASE_URL = UrlUtil.getBaseUrl() + "/image/";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String fileName;
}
