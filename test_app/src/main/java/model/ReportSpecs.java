package model;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class ReportSpecs {

    /**
     *   This method is used for setting params to query
     *
     *   @param startDate the Date that selected as starting
     *   @param endDate the Date that selected as ending
     *   @param performer the param, that select as performer
     *   @return an Specification with query criteria parameters
     */
    public static Specification<Report> checkParams(final Date startDate, final Date endDate, final String performer) {
        return new Specification<Report>() {

            List<Predicate> predicates = new ArrayList<Predicate>();


            public Predicate toPredicate(Root<Report> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if(startDate != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.<Date>get("startDate"), startDate));
                }
                if(endDate != null) {
                    predicates.add(cb.lessThanOrEqualTo(root.<Date>get("endDate"), endDate));
                }
                if(performer != null && !performer.isEmpty())
                    predicates.add(cb.like(root.<String>get("performer"), performer));


               return cb.and( predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }

}
