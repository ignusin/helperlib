package ig.helperlib.web.crud;

public interface MapperToDTO<TDTO, TEntity> {
    TDTO toDTO(TEntity entity);
}
