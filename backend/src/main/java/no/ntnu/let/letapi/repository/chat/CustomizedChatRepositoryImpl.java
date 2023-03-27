package no.ntnu.let.letapi.repository.chat;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import no.ntnu.let.letapi.model.chat.Chat;
import no.ntnu.let.letapi.model.user.User;
import no.ntnu.let.letapi.repository.listing.ListingRepository;
import no.ntnu.let.letapi.repository.user.UserRepository;

import java.util.List;

/**
 * {@inheritDoc}
 */
@RequiredArgsConstructor
public class CustomizedChatRepositoryImpl implements CustomizedChatRepository {
    private final EntityManager entityManager;
    private final ListingRepository listingRepository;
    private final UserRepository userRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Chat> getChatsByUser(User user) {
        return entityManager
                .createQuery("SELECT c FROM Chat c WHERE c.buyer = :user OR c.listing.seller = :user", Chat.class)
                .setParameter("user", user)
                .getResultList();
    }
}
