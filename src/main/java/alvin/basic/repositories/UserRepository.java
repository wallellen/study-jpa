package alvin.basic.repositories;

import alvin.basic.entities.User;
import alvin.core.repositories.RepositorySupport;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import java.util.List;

@Singleton
public class UserRepository extends RepositorySupport<User> {

    @Inject
    public UserRepository(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    public List<User> findAll() {
        return list("from User order by name");
    }
}
