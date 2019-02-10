package ig.helperlib.crud;

import java.util.List;

import ig.helperlib.entities.IdEntity;
import ig.helperlib.fetch.FetchOptions;

public interface CrudService<TEntity extends IdEntity> {
	List<TEntity> findAll(FetchOptions fetchOptions);
	long count();
	long count(String filter);

	TEntity findById(long id);
	
	TEntity save(TEntity entity);
	void remove(TEntity entity);
	void removeById(long id);
}
