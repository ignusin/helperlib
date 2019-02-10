package ig.helperlib.crud;

import ig.helperlib.entities.IdEntity;
import ig.helperlib.persistence.Repository;

public class RepositoryServiceRule<T extends IdEntity, TRepository extends Repository<T>>
	extends BaseServiceRule<T> {

	private final TRepository repository;
	
	public RepositoryServiceRule(TRepository repository) {
		this.repository = repository;
	}
	
	protected TRepository getRepository() {
		return repository;
	}
}
