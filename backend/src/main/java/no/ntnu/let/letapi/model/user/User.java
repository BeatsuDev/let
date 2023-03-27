package no.ntnu.let.letapi.model.user;

import jakarta.persistence.*;
import lombok.*;
import no.ntnu.let.letapi.model.listing.Listing;

import java.util.List;

/**
 * Model for a user
 */
@Getter
@Setter
@Entity(name = "user_") // user is a reserved word in h2
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean admin;

    @ManyToMany
    private List<Listing> favorites;
}
