package alvin.txns;

import alvin.basic.entities.Person;
import alvin.builders.PersonBuilder;
import alvin.configs.TestSupport;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class TransactionTest extends TestSupport {

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
}
