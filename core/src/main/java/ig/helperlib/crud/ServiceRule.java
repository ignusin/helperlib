package ig.helperlib.crud;

import ig.helperlib.entities.IdEntity;
import ig.helperlib.fetch.Specification;

public interface ServiceRule<T extends IdEntity> {
	void beforeCreate(T entity);
	void afterCreate(T entity);
	void beforeUpdate(T entity);
	void afterUpdate(T entity);
	void beforeRemove(T entity);
	void afterRemove(T entity);

	Specification<T> filterRead(Specification<T> spec);
}
