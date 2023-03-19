package no.ntnu.let.letapi.model.listing;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import no.ntnu.let.letapi.model.User;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Listing {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String shortDescription;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Date created;

    @Column
    private Date sold;

    @Column
    private int price;

    @ManyToOne
    private Category category;

    @OneToOne
    private Location location;

    @ManyToOne
    private User seller;

    @OneToOne
    private Image thumbnail;

    @ManyToMany
    private List<Image> gallery;

    @Enumerated(EnumType.STRING)
    private ListingState state;
}
