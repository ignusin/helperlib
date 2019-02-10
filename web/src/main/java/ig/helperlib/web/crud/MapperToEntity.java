package ig.helperlib.web.crud;

public interface MapperToEntity<TDTO, TEntity> {
    TEntity toEntity(TDTO dto);
}
