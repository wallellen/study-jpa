package alvin.basic.repositories;

import alvin.basic.entities.Interest;
import alvin.core.repositories.RepositorySupport;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.persistence.EntityManager;

@Singleton
public class InterestRepository extends RepositorySupport<Interest> {

    @Inject
    public InterestRepository(Provider<EntityManager> emProvider) {
        super(emProvider);
    }
}
