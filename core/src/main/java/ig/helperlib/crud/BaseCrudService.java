package ig.helperlib.crud;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.ListUtils;

import org.springframework.transaction.annotation.Transactional;

import ig.helperlib.entities.IdEntity;
import ig.helperlib.fetch.FetchOptions;
import ig.helperlib.fetch.Specification;
import ig.helperlib.persistence.EntityChecker;
import ig.helperlib.persistence.Repository;

public class BaseCrudService<TEntity extends IdEntity, TRepository extends Repository<TEntity>>
	implements CrudService<TEntity> {
	
	private final TRepository repository;
	private final List<ServiceRule<TEntity>> rules;
	
	public BaseCrudService(TRepository repository) {
		this(repository, Collections.emptyList());
	}
	
	public BaseCrudService(TRepository repository, List<ServiceRule<TEntity>> rules) {
		this.repository = repository;
		this.rules = ListUtils.unmodifiableList(rules);
	}
	
	protected TRepository getRepository() {
		return repository;
	}
	
	protected List<ServiceRule<TEntity>> getRules() {
		return rules;
	}
	
	@Override
	@Transactional
	public List<TEntity> findAll(FetchOptions fetchOptions) {
		Specification<TEntity> spec = makeFilterSpec(fetchOptions.getFilter());
		for (ServiceRule<TEntity> rule: getRules()) {
			spec = rule.filterRead(spec);
		}

		return repository.findAll(
			spec,
			fetchOptions.getSorting(),
			fetchOptions.getPaging()
		);
	}

	private Specification<TEntity> makeFilterSpec(String filter) {
		return filter != null
			? FilterSpec.fromClass(getRepository().getEntityClass(), filter)
			: null;
	}

	@Override
	@Transactional
	public long count() {
		return count(null);
	}

	@Override
	@Transactional
	public long count(String filter) {
		Specification<TEntity> spec = makeFilterSpec(filter);
		for (ServiceRule<TEntity> rule: getRules()) {
			spec = rule.filterRead(spec);
		}

		return getRepository().count(spec);
	}

	@Override
	@Transactional
	public TEntity findById(long id) {
		return repository.findById(id);
	}

	@Override
	@Transactional
	public TEntity save(TEntity entity) {
		boolean isNew = new EntityChecker<>(entity).isNew();

		if (isNew) {
			getRules().forEach(x -> x.beforeCreate(entity));
		}
		else {
			getRules().forEach(x -> x.beforeUpdate(entity));
		}
		
		TEntity result = repository.save(entity);

		if (isNew) {
			getRules().forEach(x -> x.afterCreate(result));
		}
		else {
			getRules().forEach(x -> x.afterUpdate(result));
		}

		repository.save(result);
		
		return result;
	}

	@Override
	@Transactional
	public void remove(TEntity entity) {
		getRules().forEach(y -> y.beforeRemove(entity));
		repository.delete(entity);
		getRules().forEach(y -> y.afterRemove(entity));
	}

	@Override
	@Transactional
	public void removeById(long id) {
		TEntity entity = repository.findById(id);

		if (entity != null) {
			remove(entity);
		}
	}
}
