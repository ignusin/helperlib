package ig.helperlib.persistence;

import java.util.List;

import ig.helperlib.entities.IdEntity;
import ig.helperlib.fetch.Paging;
import ig.helperlib.fetch.Sorting;
import ig.helperlib.fetch.Specification;

public interface Repository<T extends IdEntity> {
	Class<T> getEntityClass();

	T findById(long id);
	List<T> findAll();
	List<T> findAll(Sorting sorting);
	List<T> findAll(Sorting sorting, Paging paging);
	List<T> findAll(Specification<T> spec);
	List<T> findAll(Specification<T> spec, Sorting sorting);
	List<T> findAll(Specification<T> spec, Sorting sorting, Paging paging);
	T findOne(Specification<T> spec);

	long count();
	long count(Specification<T> spec);

	T save(T entity);
	void delete(T entity);
	void deleteById(long id);
}
