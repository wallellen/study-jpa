package alvin.basic;

import alvin.basic.entities.Person;
import alvin.basic.services.PersonService;
import alvin.builders.PersonBuilder;
import alvin.configs.TestSupport;
import com.google.inject.Inject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class ServiceTest extends TestSupport {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceTest.class);

    @Inject
    private PersonService personService;

    @Test
    public void test_save_person() {
        Person expectedPerson = withBuilder(PersonBuilder.class).build();
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