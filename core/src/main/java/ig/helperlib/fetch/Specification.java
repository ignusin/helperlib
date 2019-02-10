package ig.helperlib.fetch;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface Specification<T> {
    Predicate toPredicate(Root<T> root, CriteriaBuilder cb);

    default Specification<T> and(Specification<T> other) {
        return (r, cb) -> other != null ? cb.and(toPredicate(r, cb), other.toPredicate(r, cb)) : toPredicate(r, cb);
    }

    default Specification<T> or(Specification<T> other) {
        return (r, cb) -> other != null ? cb.or(toPredicate(r, cb), other.toPredicate(r, cb)) : toPredicate(r, cb);
    }

    default Specification<T> not() {
        return (r, cb) -> cb.not(toPredicate(r, cb));
    }
}
