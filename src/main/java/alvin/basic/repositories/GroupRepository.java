package alvin.basic.repositories;

import alvin.basic.entities.Group;
import alvin.basic.entities.User;
import alvin.core.repositories.RepositorySupport;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import java.util.List;

@Singleton
public class GroupRepository extends RepositorySupport<Group> {

    @Inject
    public GroupRepository(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    public List<Group> findAll() {
        return list("from Group");
    }
}
