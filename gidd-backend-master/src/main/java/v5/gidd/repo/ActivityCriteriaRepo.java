package v5.gidd.repo;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import v5.gidd.entities.activity.Activity;
import v5.gidd.entities.activity.attributes.ActivityLevel;
import v5.gidd.entities.activity.attributes.ActivityStatus;
import v5.gidd.entities.activity.filter.ActivityPage;
import v5.gidd.entities.activity.filter.ActivitySearchCriteria;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Repository used to fetch specific activities given a some criteria. Useful when filtering activities.
 */

@Repository
public class ActivityCriteriaRepo {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public ActivityCriteriaRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    /**
     * Yields a page containing activities which conform to the criteria.
     * @param activityPage Specifies how returned page should be formatted.
     * @param activitySearchCriteria Criteria for which activities to retreive.
     * @return Page of various activities.
     */

    public Page<Activity> findAllWithFilters(ActivityPage activityPage,
                                             ActivitySearchCriteria activitySearchCriteria) {
        CriteriaQuery<Activity> criteriaQuery = criteriaBuilder.createQuery(Activity.class);
        Root<Activity> activityRoot = criteriaQuery.from(Activity.class);
        Predicate predicate = getPredicate(activitySearchCriteria, activityRoot);
        criteriaQuery.where(predicate);
        setOrder(activityPage, criteriaQuery, activityRoot);

        TypedQuery<Activity> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(activityPage.getPageNumber() * activityPage.getPageSize());
        typedQuery.setMaxResults(activityPage.getPageSize());

        Pageable pageable = getPageable(activityPage);

        long activitysCount = getActivitysCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, activitysCount);
    }

    /**
     * Constructs the predicate used to query the database.
     * @param activitySearchCriteria The search criteria.
     * @param activityRoot The initial part of the query to build.
     * @return The predicate used to query the database.
     */

    private Predicate getPredicate(ActivitySearchCriteria activitySearchCriteria,
                                   Root<Activity> activityRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(activitySearchCriteria.getName()) && !activitySearchCriteria.getName().trim().equals("")) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(activityRoot.get("name")),
                    "%" + activitySearchCriteria.getName().toLowerCase() + "%"));
        }
        if (Objects.nonNull(activitySearchCriteria.getStartTime())) {
            predicates.add(criteriaBuilder.equal(criteriaBuilder.function("TRUNC", java.sql.Date.class, activityRoot.get("startTime")),
                    criteriaBuilder.function("TO_DATE", java.sql.Date.class, criteriaBuilder.literal(activitySearchCriteria.getStartTime().toString()),
                            criteriaBuilder.literal("yyyy-mm-dd"))));
        }


        if (!activitySearchCriteria.showPreviousActivities()) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(activityRoot.get("endTime"), LocalDateTime.now()));
        }

        predicates.add(criteriaBuilder.equal(activityRoot.get("status"), ActivityStatus.ACTIVE));

        if (Objects.nonNull(activitySearchCriteria.getLocations())) {

            List<Predicate> locationPredicates = new ArrayList<>();
            for (String location : activitySearchCriteria.getLocations()) {
                locationPredicates.add(criteriaBuilder.equal(activityRoot.get("location").get("city"), location));
            }
            predicates.add(criteriaBuilder.or(locationPredicates.toArray(Predicate[]::new)));
        }

        if (Objects.nonNull(activitySearchCriteria.getInterests())) {
            List<Predicate> interestPredicates = new ArrayList<>();
            for (String interest : activitySearchCriteria.getInterests()) {
                interestPredicates.add(criteriaBuilder.isMember(interest, activityRoot.get("interests")));
            }
            predicates.add(criteriaBuilder.or(interestPredicates.toArray(Predicate[]::new)));
        }

        if (Objects.nonNull(activitySearchCriteria.getActivityLevels())) {
            List<Predicate> activityLevelsPredicates = new ArrayList<>();
            for (ActivityLevel activityLevel : activitySearchCriteria.getActivityLevels()) {
                activityLevelsPredicates.add(criteriaBuilder.equal(activityRoot.get("activityLevel"), activityLevel));
            }
            predicates.add(criteriaBuilder.or(activityLevelsPredicates.toArray(Predicate[]::new)));
        }

        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }

    /**
     * Mutates a query to specify a sorting strategy.
     * @param activityPage Page containing the sorting strategy.
     * @param criteriaQuery The current query.
     * @param activityRoot The root of the query.
     */

    private void setOrder(ActivityPage activityPage, CriteriaQuery<Activity> criteriaQuery, Root<Activity> activityRoot) {
        if(activityPage.getSortDirection().equals(Sort.Direction.ASC)){
            criteriaQuery.orderBy(criteriaBuilder.asc(activityRoot.get(activityPage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(activityRoot.get(activityPage.getSortBy())));
        }
    }

    /**
     * Retrieves page information from activity page implementation.
     * @param activityPage Any activity page.
     * @return The page information.
     */

    private Pageable getPageable(ActivityPage activityPage) {
        Sort sort = Sort.by(activityPage.getSortDirection(), activityPage.getSortBy());
        return PageRequest.of(activityPage.getPageNumber(),activityPage.getPageSize(), sort);
    }

    /**
     * The activity-count from a query formed by the provided predicate.
     * @param predicate The predicate to find count for.
     * @return The count.
     */

    private long getActivitysCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Activity> countRoot = countQuery.from(Activity.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
