package ig.helperlib.web.crud;

public interface Mapper<TRequestDTO, TResponseDTO, TEntity>
		extends MapperToDTO<TResponseDTO, TEntity>, MapperToEntity<TRequestDTO, TEntity> {
}
