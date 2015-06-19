package model;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

public final class ReportSpecs {

    public static Specification<Report> checkParams(final Date startDate, final Date endDate, final String performers) {
        return new Specification<Report>() {


            public Predicate toPredicate(Root<Report> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicateStart = null;
                Predicate predicateEnd = null;
                Predicate predicatePerformer = null;
                if(startDate != null) {
                    predicateStart = cb.greaterThanOrEqualTo(root.<Date>get("startDate"), startDate);
                }
                if(endDate != null) {
                    predicateEnd = cb.lessThanOrEqualTo(root.<Date>get("endDate"), endDate);
                }
                if(performers != null && performers.isEmpty())
                    predicatePerformer = cb.like(root.<String>get("performner"), performers);


                return cb.and(predicateStart,predicateEnd,predicatePerformer);
            }
        };
    }

}
