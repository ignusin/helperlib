package ig.helperlib.persistence;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import ig.helperlib.entities.IdEntity;
import ig.helperlib.fetch.Paging;
import ig.helperlib.fetch.Sorting;
import ig.helperlib.fetch.SortingDirection;
import ig.helperlib.fetch.Specification;

public class DefaultRepository<T extends IdEntity> implements Repository<T> {
    private static class CriteriaContext<T> {
        final CriteriaQuery<T> query;
        final Root<T> root;
        final CriteriaBuilder builder;

        CriteriaContext(CriteriaQuery<T> query, Root<T> root, CriteriaBuilder builder) {
            this.query = query;
            this.root = root;
            this.builder = builder;
        }
    }

    private final EntityManager em;
    private final Class<T> entityClass;

    public DefaultRepository(EntityManager em, Class<T> entityClass) {
        this.em = em;
        this.entityClass = entityClass;
    }

    @Override
    public Class<T> getEntityClass() {
        return entityClass;
    }

    @Override
    public T findById(long id) {
        return em.find(getEntityClass(), id);
    }

    @Override
    public List<T> findAll() {
        return findAll(null, null, null);
    }

    @Override
    public List<T> findAll(Sorting sorting) {
        return findAll(null, sorting, null);
    }

    @Override
    public List<T> findAll(Sorting sorting, Paging paging) {
        return findAll(null, sorting, paging);
    }

    @Override
    public List<T> findAll(Specification<T> spec) {
        return findAll(spec, null, null);
    }

    @Override
    public List<T> findAll(Specification<T> spec, Sorting sorting) {
        return findAll(spec, sorting, null);
    }

    @Override
    public List<T> findAll(Specification<T> spec, Sorting sorting, Paging paging) {
        CriteriaContext<T> cc = buildQuery(spec);

        CriteriaQuery<T> cq = cc.query;

        cq = cq.select(cc.root);

        if (sorting != null) {
            Expression<?> sortExpr = CriteriaUtility.path(cc.root, sorting.getField());
            cq = cq.orderBy(
                sorting.getDirection() == SortingDirection.ASC
                        ? cc.builder.asc(sortExpr)
                        : cc.builder.desc(sortExpr)
            );
        }

        TypedQuery<T> query = em.createQuery(cq);

        if (paging != null) {
            query.setFirstResult((paging.getStartIndex() - 1) * paging.getSize());
            query.setMaxResults(paging.getSize());
        }

        return query.getResultList();
    }

    @Override
    public T findOne(Specification<T> spec) {
        CriteriaContext<T> cc = buildQuery(spec);
        CriteriaQuery<T> cq = cc.query;

        cq = cq.select(cc.root);

        TypedQuery<T> query = em.createQuery(cq);
        query.setMaxResults(1);

        List<T> result = query.getResultList();
        return result.size() > 0 ? result.get(0) : null;
    }

    @Override
    public long count() {
        return count(null);
    }

    @Override
    public long count(Specification<T> spec) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<T> root = cq.from(getEntityClass());

        if (spec != null) {
            cq = cq.where(spec.toPredicate(root, cb));
        }

        cq = cq.select(cb.count(root));

        return em.createQuery(cq).getSingleResult();
    }

    private CriteriaContext<T> buildQuery(Specification<T> spec) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());

        if (spec != null) {
            query = query.where(spec.toPredicate(root, cb));
        }

        return new CriteriaContext<>(query, root, cb);
    }

    @Override
    public T save(T entity) {
        T result;

        if (entity.getId() == null || entity.getId() == 0) {
            em.persist(entity);
            result = entity;
        }
        else {
            if (!em.contains(entity)) {
                result = em.merge(entity);
            }
            else {
                result = entity;
            }
        }

        em.flush();

        return result;
    }

    @Override
    public void delete(T entity) {
        em.remove(entity);
        em.flush();
    }

    @Override
    public void deleteById(long id) {
        T entity = findById(id);
        if (entity != null) {
            delete(entity);
        }
    }
}
