package no.ntnu.let.letapi.model.chat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import no.ntnu.let.letapi.model.listing.Listing;
import no.ntnu.let.letapi.model.user.User;

/**
 * Model for a chat
 */
@Getter
@Setter
@Entity
public class Chat {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Listing listing;

    @ManyToOne
    private User buyer;
}
