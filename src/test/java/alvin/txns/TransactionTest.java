package alvin.txns;

import alvin.basic.entities.Person;
import alvin.basic.entities.Worker;
import alvin.builders.PersonBuilder;
import alvin.builders.WorkerBuilder;
import alvin.configs.TestSupport;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class TransactionTest extends TestSupport {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionTest.class);

    private static final AtomicInteger SERIALIZER = new AtomicInteger(1);

    @Test
    public void test_save_and_update() throws Exception {
        Person expectedPerson = withBuilder(PersonBuilder.class).build();

        em.getTransaction().begin();
        try {
            em.persist(expectedPerson);
            assertThat(expectedPerson.getId(), is(notNullValue()));

            expectedPerson.setName("Auth");
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
        em.clear();

        Person actualPerson = em.find(Person.class, expectedPerson.getId());
        assertThat(actualPerson.getName(), is("Arthur"));
    }

    @Test
    public void test_find_and_update() throws Exception {
        Person expectedPerson = withBuilder(PersonBuilder.class).create();

        em.getTransaction().begin();
        try {
            Person actualPerson = em.find(Person.class, expectedPerson.getId());
            actualPerson.setName("Arthur");
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
        em.clear();

        Person actualPerson = em.find(Person.class, expectedPerson.getId());
        assertThat(actualPerson.getName(), is("Arthur"));
    }

    @Test
    public void test_query_and_lock() throws Exception {
        LOGGER.info("create worker");
        Worker expectedWorker = withBuilder(WorkerBuilder.class).create();

        LOGGER.info("first update");
        updateWorker(expectedWorker.getId(), expectedWorker.getVersion());

        LOGGER.info("second update");
        updateWorker(expectedWorker.getId(), expectedWorker.getVersion());
    }

    private void updateWorker(int workerId, int expectedVersion) {
        Worker actualWorker;
        em.getTransaction().begin();
        try {
            actualWorker = em.find(Worker.class, workerId, LockModeType.PESSIMISTIC_WRITE);
            actualWorker.setName("Arthur" + SERIALIZER.getAndIncrement());
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
        em.clear();

        actualWorker = em.find(Worker.class, workerId);
        assertThat(actualWorker.getVersion(), not(expectedVersion));
    }

    @Override
    protected String[] getTruncateTables() {
        return new String[]{"person", "worker"};
    }
}
