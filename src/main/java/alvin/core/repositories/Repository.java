package alvin.core.repositories;

import javax.persistence.EntityManager;
import java.util.Optional;

public interface Repository<T> {
    Optional<T> find(Object key);
    void save(T model);
    void saveOrUpdate(T model);
    void delete(T model);
    EntityManager em();
}
