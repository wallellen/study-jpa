package alvin.configs;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.jpa.JpaPersistModule;
import org.junit.Before;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public abstract class TestSupport {

    @Inject
    protected EntityManager em;

    @Inject
    private Injector injector;

    @Before
    public void setUp() {
        Guice.createInjector(new JpaPersistModule("default")).injectMembers(this);

        em.getTransaction().begin();
        try {
            for (String table : truncateTables()) {
                em.createNativeQuery("truncate table " + table).executeUpdate();
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    protected List<String> truncateTables() {
        return newArrayList("person");
    }

    protected <E> E withBuilder(Class<E> entityClass) {
        return injector.getInstance(entityClass);
    }
}
