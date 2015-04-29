package alvin.configs;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;
import org.junit.Before;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public abstract class TestSupport {
    private Injector injector;

    @Inject
    protected EntityManager em;

    @Before
    public void setUp() {
        injector = Guice.createInjector(new JpaPersistModule("default"));
        injector.getInstance(PersistService.class).start();
        injector.injectMembers(this);

        em.getTransaction().begin();
        try {
            for (String table : getTruncateTables()) {
                em.createNativeQuery("truncate table " + table).executeUpdate();
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
    }

    protected abstract String[] getTruncateTables();

    protected <E> E withBuilder(Class<E> entityClass) {
        return injector.getInstance(entityClass);
    }
}
