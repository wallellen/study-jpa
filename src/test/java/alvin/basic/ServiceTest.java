package alvin.basic;

import alvin.basic.entities.Person;
import alvin.basic.services.PersonService;
import alvin.configs.TestSupport;
import com.google.inject.Inject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class ServiceTest extends TestSupport {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceTest.class);

    @Inject
    private PersonService personService;

    @Test
    public void test_save_person() {
        Person expectedPerson = createPerson();
        LOGGER.info("before persist: {}", expectedPerson);

        personService.save(expectedPerson);

        assertThat(expectedPerson.getId(), is(notNullValue()));
        LOGGER.info("after persist: {}", expectedPerson);

        em.clear();

        Person actualPerson = em.find(Person.class, expectedPerson.getId());
        LOGGER.info("after load: {}", actualPerson);

        assertThat(expectedPerson.toString(), is(actualPerson.toString()));
    }

    private Person createPerson() {
        Person person = new Person();
        person.setName("Alvin");
        person.setGender("M");
        person.setEmail("alvin@fakeaddr.com");
        person.setTelephone("13999999999");
        person.setBirthday(LocalDateTime.of(1981, 3, 17, 0, 0).atOffset(ZoneOffset.UTC).toLocalDateTime());
        return person;
    }

    @Override
    protected String[] getTruncateTables() {
        return new String[]{"worker"};
    }
}