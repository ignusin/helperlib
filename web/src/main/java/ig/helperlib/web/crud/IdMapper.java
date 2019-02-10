package ig.helperlib.web.crud;

import ig.helperlib.entities.IdEntity;

public interface IdMapper<TRequestDTO extends RequestDTO, TResponseDTO, TEntity extends IdEntity>
        extends Mapper<TRequestDTO, TResponseDTO, TEntity> {
}
