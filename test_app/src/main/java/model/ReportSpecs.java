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

    public static Specification<Report> checkParams(final Date startDate, final Date endDate, final String performers) {
        return new Specification<Report>() {

            List<Predicate> predicates = new ArrayList<Predicate>();


            public Predicate toPredicate(Root<Report> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if(startDate != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.<Date>get("startDate"), startDate));
                }
                if(endDate != null) {
                    predicates.add(cb.lessThanOrEqualTo(root.<Date>get("endDate"), endDate));
                }
                if(performers != null && !performers.isEmpty())
                    predicates.add(cb.like(root.<String>get("performer"), performers));


               return cb.and( predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }

}
