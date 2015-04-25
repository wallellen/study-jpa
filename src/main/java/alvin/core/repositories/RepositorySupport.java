package alvin.core.repositories;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

public class RepositorySupport<T> implements Repository<T> {
    private Provider<EntityManager> emProvider;
    private Class<T> entityClass;

    protected RepositorySupport(Provider<EntityManager> emProvider) {
        this.emProvider = emProvider;

        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    protected Optional<T> findBy(String ql, Statement statement) {
        return first(list(ql, statement));
    }

    protected Optional<T> findBy(String ql) {
        return findBy(ql, null);
    }

    protected List<T> list(String ql, Statement statement) {
        TypedQuery<T> query = em().createQuery(ql, entityClass);
        if (statement != null) {
            statement.prepare(query);
        }
        return query.getResultList();
    }

    protected List<T> list(String ql) {
        return list(ql, null);
    }

    protected long count(String ql, Statement statement) {
        Query query = em().createQuery(ql);
        if (statement != null) {
            statement.prepare(query);
        }
        return (Long) query.getSingleResult();
    }

    protected long count(String ql) {
        return count(ql, null);
    }

    protected int execute(String ql, Statement statement) {
        Query query = em().createQuery(ql);
        if (statement != null) {
            statement.prepare(query);
        }
        return query.executeUpdate();
    }

    protected int execute(String ql) {
        return execute(ql, null);
    }

    @Override
    public Optional<T> find(Object key) {
        return Optional.ofNullable(em().find(entityClass, key));
    }

    @Override
    public void save(T model) {
        em().persist(model);
    }

    @Override
    public void saveOrUpdate(T model) {
        em().merge(model);
    }

    @Override
    public void delete(T model) {
        em().remove(model);
    }

    @Override
    public EntityManager em() {
        return emProvider.get();
    }

    protected <E> Optional<E> first(List<E> list) {
        return list.isEmpty() ? Optional.<E>empty() : Optional.of(list.get(0));
    }
}
