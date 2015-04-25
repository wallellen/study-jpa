package alvin.configs;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TestModule extends AbstractModule {
    private static final ThreadLocal<EntityManager> ENTITY_MANAGER_CACHE = new ThreadLocal<>();

    @Override
    protected void configure() {
    }

    @Provides
    @Singleton
    public EntityManagerFactory provideEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("default");
    }

    @Provides
    @Inject
    public EntityManager provideEntityManager(EntityManagerFactory entityManagerFactory) {
        EntityManager em = ENTITY_MANAGER_CACHE.get();
        if (em == null) {
            em = entityManagerFactory.createEntityManager();
            ENTITY_MANAGER_CACHE.set(em);
        }
        return em;
    }
}
