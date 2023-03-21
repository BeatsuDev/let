package no.ntnu.let.letapi.repository.listing;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import no.ntnu.let.letapi.model.listing.Listing;
import no.ntnu.let.letapi.util.ListingFilter;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

public class CustomizedListingRepositoryImpl implements CustomizedListingRepository {
    private final EntityManager entityManager;
    private final ListingRepository listingRepository;

    @Lazy
    protected CustomizedListingRepositoryImpl(EntityManager entityManager, ListingRepository listingRepository) {
        this.entityManager = entityManager;
        this.listingRepository = listingRepository;
    }

    private <T> Predicate searchLowerCase(CriteriaBuilder cb, Root<T> root, String field, String value) {
        return cb.like(cb.lower(root.get(field)), "%" + value.toLowerCase() + "%");
    }

    public Page<Listing> findAll(ListingFilter filter, PageRequest pageRequest) {
        if (filter == null) return listingRepository.findAll(pageRequest);

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

        // TODO: Add favorites filter

        Predicate stateFilter;
        if (filter.getStates() != null) {
            stateFilter = cb.in(listing.get("state")).value(filter.getStates());
        } else {
            stateFilter = cb.conjunction();
        }
        Predicate finalFilter = cb.and(searchString, categoryFilter, userIdFilter, stateFilter);
        TypedQuery<Listing> query = entityManager.createQuery(cq.select(listing).where(finalFilter));

        query.setFirstResult(pageRequest.getPageNumber() * pageRequest.getPageSize());
        query.setMaxResults(pageRequest.getPageSize());
        List<Listing> listings = query.getResultList();
        return PageableExecutionUtils.getPage(listings, pageRequest, () -> query.getResultList().size());
    }
}
