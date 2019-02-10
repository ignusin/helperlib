package ig.helperlib.persistence;

import ig.helperlib.entities.IdEntity;

public class EntityChecker<T extends IdEntity> {
    private final T entity;

    public EntityChecker(T entity) {
        this.entity = entity;
    }

    public boolean isNew() {
        return entity.getId() == null || entity.getId() == 0L;
    }
}
