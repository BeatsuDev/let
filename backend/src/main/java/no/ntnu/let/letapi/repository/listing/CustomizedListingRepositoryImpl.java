package no.ntnu.let.letapi.repository.listing;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import no.ntnu.let.letapi.model.listing.Listing;
import no.ntnu.let.letapi.model.user.User;
import no.ntnu.let.letapi.repository.user.UserRepository;
import no.ntnu.let.letapi.util.ListingFilter;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

public class CustomizedListingRepositoryImpl implements CustomizedListingRepository {
    private final EntityManager entityManager;
    private final ListingRepository listingRepository;
    private final UserRepository userRepository;

    @Lazy
    protected CustomizedListingRepositoryImpl(EntityManager entityManager, ListingRepository listingRepository,
                                              UserRepository userRepository) {
        this.entityManager = entityManager;
        this.listingRepository = listingRepository;
        this.userRepository = userRepository;
    }

    private <T> Predicate searchLowerCase(CriteriaBuilder cb, Root<T> root, String field, String value) {
        return cb.like(cb.lower(root.get(field)), "%" + value.toLowerCase() + "%");
    }

    private Predicate getFilterPredicate(@NotNull ListingFilter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Listing> cq = cb.createQuery(Listing.class);
        Root<Listing> listing = cq.from(Listing.class);

        Predicate searchString;
        if (filter.getSearchString() != null) {
            Predicate titleSearchString = searchLowerCase(cb, listing, "title", filter.getSearchString());
            Predicate summarySearchString = searchLowerCase(cb, listing, "summary", filter.getSearchString());
            Predicate descriptionSearchString = searchLowerCase(cb, listing, "description", filter.getSearchString());

            searchString = cb.or(titleSearchString, summarySearchString, descriptionSearchString);
        } else {
            searchString = cb.conjunction();
        }

        // TODO: Add location filter

        Predicate categoryFilter;
        if (filter.getCategories() != null) {
            categoryFilter = cb.in(listing.get("category")).value(filter.getCategories());
        } else {
            categoryFilter = cb.conjunction();
        }

        Predicate userIdFilter;
        if (filter.getUserId() != null) {
            userIdFilter = cb.equal(listing.get("seller"), filter.getUserId());
        } else {
            userIdFilter = cb.conjunction();
        }

        Predicate favoritesFilter;
        if (filter.getFavoritesOf() != null) {
            User user = userRepository.findById(filter.getFavoritesOf().getId()).orElse(null);
            if (user == null) {
                favoritesFilter = cb.disjunction();
            } else {
                favoritesFilter = cb.in(listing.get("id")).value(user.getFavorites().stream().map(Listing::getId).toList());
            }
        } else {
            favoritesFilter = cb.conjunction();
        }

        Predicate stateFilter;
        if (filter.getStates() != null) {
            stateFilter = cb.in(listing.get("state")).value(filter.getStates());
        } else {
            stateFilter = cb.conjunction();
        }

        return cb.and(searchString, categoryFilter, userIdFilter, favoritesFilter, stateFilter);
    }

    public Page<Listing> findAll(ListingFilter filter, PageRequest pageRequest) {
        if (filter == null) return listingRepository.findAll(pageRequest);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        CriteriaQuery<Listing> cq = cb.createQuery(Listing.class);
        Root<Listing> listing = cq.from(Listing.class);

        Predicate finalFilter = getFilterPredicate(filter);
        CriteriaQuery<Long> count = countQuery.select(cb.count(countQuery.from(Listing.class)))
                .where(finalFilter);
        long countResult = entityManager.createQuery(count).getSingleResult();

        TypedQuery<Listing> query = entityManager.createQuery(cq.select(listing).where(finalFilter));
        query.setFirstResult(pageRequest.getPageNumber() * pageRequest.getPageSize());
        query.setMaxResults(pageRequest.getPageSize());
        List<Listing> listings = query.getResultList();
        return PageableExecutionUtils.getPage(listings, pageRequest, () -> countResult);
    }
}
