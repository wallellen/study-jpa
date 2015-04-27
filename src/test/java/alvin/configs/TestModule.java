package alvin.configs;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.matcher.Matchers;
import com.google.inject.persist.Transactional;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import javax.inject.Provider;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TestModule extends AbstractModule {
    private static final ThreadLocal<EntityManager> ENTITY_MANAGER_CACHE = new ThreadLocal<>();

    @Override
    protected void configure() {
        MethodInterceptor transactionInterceptor = new TransactionInterceptor();
        requestInjection(transactionInterceptor);
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(Transactional.class), transactionInterceptor);
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

    private static class TransactionInterceptor implements MethodInterceptor {

        @Inject
        private Provider<EntityManager> emProvides;

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            EntityManager em = emProvides.get();
            em.getTransaction().begin();
            try {
                Object result = invocation.proceed();
                em.getTransaction().commit();
                return result;
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw e;
            }
        }
    }
}
