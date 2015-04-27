package alvin.basic;

import alvin.basic.entities.Person;
import alvin.builders.PersonBuilder;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class PersistenceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceTest.class);

    private EntityManager em;

    private static final String[] TABLE_LIST = {"person"};

    @Before
    public void setUp() {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("default");
        em = emFactory.createEntityManager();

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

        em.getTransaction().begin();
        try {
            em.persist(expectedPerson);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
        assertThat(expectedPerson.getId(), is(notNullValue()));
        LOGGER.info("after persist: {}", expectedPerson);

        em.clear();

        Person actualPerson = em.find(Person.class, expectedPerson.getId());
        LOGGER.info("after load: {}", actualPerson);

        assertThat(expectedPerson.toString(), is(actualPerson.toString()));
    }
}