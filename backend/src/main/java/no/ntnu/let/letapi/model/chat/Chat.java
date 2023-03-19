package no.ntnu.let.letapi.model.chat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import no.ntnu.let.letapi.model.User;
import no.ntnu.let.letapi.model.listing.Listing;

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
