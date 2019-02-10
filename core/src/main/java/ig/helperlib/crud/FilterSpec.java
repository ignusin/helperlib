package ig.helperlib.crud;

import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import ig.helperlib.entities.IdEntity;
import ig.helperlib.fetch.Specification;
import ig.helperlib.persistence.CriteriaUtility;

public class FilterSpec<T extends IdEntity> implements Specification<T> {
    public static <T extends IdEntity> FilterSpec<T> fromClass(Class<T> cls, String filter) {
        return new FilterSpec<>(filter, FilterableCache.getInstance().getFieldsFor(cls));
    }


    private final String filter;
    private final List<String> filterFields;

    public FilterSpec(String filter, List<String> filterFields) {
        if (filter == null) {
            throw new IllegalArgumentException("filter");
        }

        if (filterFields.size() == 0) {
            throw new IllegalArgumentException("filterFields");
        }

        this.filter = filter;
        this.filterFields = filterFields;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaBuilder cb) {
        Predicate result = null;
        String filter = "%" + this.filter + "%";

        for (String filterField: filterFields) {
            Predicate part = cb.like(CriteriaUtility.path(root, filterField), filter);

            if (result != null) {
                result = cb.or(result, part);
            }
            else {
                result = part;
            }
        }

        return result;
    }
}
