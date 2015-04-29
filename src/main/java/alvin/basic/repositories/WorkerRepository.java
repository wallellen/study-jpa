package alvin.basic.repositories;

import alvin.basic.entities.Worker;
import alvin.core.repositories.RepositorySupport;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.persistence.EntityManager;

@Singleton
public class WorkerRepository extends RepositorySupport<Worker> {

    @Inject
    public WorkerRepository(Provider<EntityManager> emProvider) {
        super(emProvider);
    }
}
