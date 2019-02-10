package ig.helperlib.persistence;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

public class CriteriaUtility {
    @SuppressWarnings("unchecked")
    public static <T, U> Expression<U> path(Root<T> root, String path) {
        String[] tokens = path.split("\\.");

        Path result = root;
        for (String token: tokens) {
            result = result.get(token);
        }

        return result.as(String.class);
    }
}
