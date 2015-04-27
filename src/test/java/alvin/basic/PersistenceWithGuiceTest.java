package alvin.basic;

import alvin.basic.entities.Person;
import alvin.basic.services.PersonService;
import alvin.builders.PersonBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Provides;
import com.google.inject.matcher.Matchers;
import com.google.inject.persist.Transactional;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class PersistenceWithGuiceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceWithGuiceTest.class);
    private static final String[] TABLE_LIST = {"person"};

    @Inject
    private PersonService personService;

    @Inject EntityManager em;

    @Before
    public void setUp() {
        Guice.createInjector(new TestModule()).injectMembers(this);
        em.getTransaction().begin();
        try {
            for (String table : TABLE_LIST) {
                em.createNativeQuery("truncate table " + table).executeUpdate();
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
    }

    @Test
    public void test_save_person() {
        Person expectedPerson = new PersonBuilder(null).build();
        LOGGER.info("before persist: {}", expectedPerson);

        personService.save(expectedPerson);

        assertThat(expectedPerson.getId(), is(notNullValue()));
        LOGGER.info("after persist: {}", expectedPerson);

        em.clear();

        Person actualPerson = em.find(Person.class, expectedPerson.getId());
        LOGGER.info("after load: {}", actualPerson);

        assertThat(expectedPerson.toString(), is(actualPerson.toString()));
    }
}


class TransactionInterceptor implements MethodInterceptor {
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

class TestModule extends AbstractModule {
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
}