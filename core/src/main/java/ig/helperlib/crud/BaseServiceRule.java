package ig.helperlib.crud;

import ig.helperlib.entities.IdEntity;
import ig.helperlib.fetch.Specification;

public class BaseServiceRule<T extends IdEntity> implements ServiceRule<T> {
	@Override
	public void beforeCreate(T entity) { }
	
	@Override
	public void afterCreate(T entity) { }
	
	@Override
	public void beforeUpdate(T entity) { }
	
	@Override
	public void afterUpdate(T entity) { }
	
	@Override
	public void beforeRemove(T entity) { }
	
	@Override
	public void afterRemove(T entity) { }

	@Override
	public Specification<T> filterRead(Specification<T> spec) { return spec; }
}
