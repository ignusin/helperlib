package ig.helperlib.web.crud;

import ig.helperlib.entities.IdEntity;
import ig.helperlib.persistence.Repository;

public abstract class RepositoryMapper<TReq extends RequestDTO, TResp, T extends IdEntity, TRepository extends Repository<T>>
		implements IdMapper<TReq, TResp, T> {

	private final TRepository repository;

	public RepositoryMapper(TRepository repository) {
		this.repository = repository;
	}

	protected TRepository getRepository() {
		return repository;
	}
}
